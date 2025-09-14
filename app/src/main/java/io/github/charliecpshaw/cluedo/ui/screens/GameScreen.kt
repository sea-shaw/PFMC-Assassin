package io.github.charliecpshaw.cluedo.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import io.github.charliecpshaw.cluedo.ui.icons.Mail
import io.github.charliecpshaw.cluedo.ui.icons.Skull
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEdit: (Long) -> Unit,
    navigateToPlayer: (Long, Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConformationRequired by rememberSaveable { mutableStateOf(false) }
    var emailConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var emailFailed by rememberSaveable { mutableStateOf(false) }
    var selectedPlayerDetails: PlayerInfo? by rememberSaveable { mutableStateOf(null) }
    var selectedPlayerToKill: PlayerInfo? by rememberSaveable { mutableStateOf(null) }
    var selectedPlayerToEmail: PlayerInfo? by rememberSaveable { mutableStateOf(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CluedoTopAppBar(
                title = uiState.name,
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                hasEditButton = true,
                onEditClick = { navigateToEdit(viewModel.gameId) },
                editContentDescriptionRes = R.string.edit_game,
                hasDeleteButton = true,
                onDeleteClick = { deleteConformationRequired = true },
                deleteContentDescriptionResId = R.string.delete_game,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { emailConfirmationRequired = true },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Mail,
                    contentDescription = stringResource(R.string.email_all_players)
                )
            }
        }
    ) { innerPadding ->
        GameBody(
            playerInfoList = uiState.players,
            onPlayerClick = { selectedPlayerDetails = it },
            onKillPlayerClick = { selectedPlayerToKill = it },
            onEmailPlayerClick = {selectedPlayerToEmail = it },
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
        if (emailFailed) {
            AlertDialogue(
                text = stringResource(R.string.email_failed_to_send),
                onDismiss = { emailFailed = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        } else if (deleteConformationRequired) {
            ConfirmationDialogue(
                dialogueText = stringResource(id = R.string.game_delete_question),
                confirmTextResId = R.string.delete,
                cancelTextResId = R.string.cancel,
                onConfirm = {
                    deleteConformationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteGame()
                        navigateBack()
                    }
                },
                onCancel = { deleteConformationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        } else if (emailConfirmationRequired) {
            val context = LocalContext.current
            ConfirmationDialogue(
                dialogueText = stringResource(id = R.string.game_email_all_confirmation),
                confirmTextResId = R.string.send,
                cancelTextResId = R.string.cancel,
                onConfirm = {
                    emailConfirmationRequired = false
                    uiState.players.forEach { playerInfo ->
                        coroutineScope.launch(Dispatchers.IO) {
                            if (playerInfo.playerEmailAddress is String) {
                                try {
                                    viewModel.emailPlayer(context, playerInfo)
                                } catch (_: Exception) {
                                    emailFailed = true
                                }
                            }
                        }
                    }
                },
                onCancel = { emailConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            )
        } else if (selectedPlayerDetails != null) {
            val player = selectedPlayerDetails!!
            ConfirmationDialogue(
                dialogueText = stringResource(
                    id = R.string.game_view_player_confirmation,
                    player.playerName,
                ),
                confirmTextResId = R.string.view,
                cancelTextResId = R.string.cancel,
                onConfirm = {
                    selectedPlayerDetails = null
                    navigateToPlayer(viewModel.gameId, player.playerId)
                },
                onCancel = { selectedPlayerDetails = null }
            )
        } else if (selectedPlayerToKill != null) {
            val player = selectedPlayerToKill!!
            ConfirmationDialogue(
                dialogueText = stringResource(
                    id = R.string.game_kill_player_confirmation,
                    player.playerName,
                ),
                confirmTextResId = R.string.confirm,
                cancelTextResId = R.string.cancel,
                onConfirm = {
                    selectedPlayerToKill = null
                    coroutineScope.launch {
                        viewModel.killPlayer(player.playerId)
                    }
                },
                onCancel = { selectedPlayerToKill = null },
            )
        } else if (selectedPlayerToEmail != null) {
            val playerInfo = selectedPlayerToEmail!!
            val context = LocalContext.current
            ConfirmationDialogue(
                dialogueText = stringResource(
                    id = R.string.game_email_player_confirmation,
                    playerInfo.playerName,
                ),
                confirmTextResId = R.string.send,
                cancelTextResId = R.string.cancel,
                onConfirm = {
                    selectedPlayerToEmail = null
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            viewModel.emailPlayer(context, playerInfo)
                        } catch (e: Exception) {
                            emailFailed = true
                            Log.d("MailSender", e.message ?: "")
                        }
                    }
                },
                onCancel = { selectedPlayerToEmail = null },
            )
        }
    }
}

@Composable
fun GameBody(
    playerInfoList: List<PlayerInfo>,
    onPlayerClick: (PlayerInfo) -> Unit,
    onKillPlayerClick: (PlayerInfo) -> Unit,
    onEmailPlayerClick: (PlayerInfo) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PlayerInfoList(
            playerInfoList = playerInfoList,
            onPlayerClick = onPlayerClick,
            onKillPlayerClick = onKillPlayerClick,
            onEmailPlayerClick = onEmailPlayerClick,
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun PlayerInfoList(
    playerInfoList: List<PlayerInfo>,
    onPlayerClick: (PlayerInfo) -> Unit,
    onKillPlayerClick: (PlayerInfo) -> Unit,
    onEmailPlayerClick: (PlayerInfo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        if (playerInfoList.isNotEmpty()) {
            items(
                items = playerInfoList,
                key = { it.playerId },
            ) { playerInfo ->
                PlayerInfoCard(
                    playerInfo = playerInfo,
                    onKillPlayerClick = onKillPlayerClick,
                    onEmailPlayerClick = onEmailPlayerClick,
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onPlayerClick(playerInfo) }
                        .animateItem()
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PlayerInfoCard(
    playerInfo: PlayerInfo,
    onKillPlayerClick: (PlayerInfo) -> Unit,
    onEmailPlayerClick: (PlayerInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = playerInfo.playerName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onKillPlayerClick(playerInfo) },
                ) {
                    Icon(
                        imageVector = Skull,
                        contentDescription = stringResource(id = R.string.kill_player),
                    )
                }
                IconButton(
                    onClick = { onEmailPlayerClick(playerInfo) },
                    enabled = playerInfo.playerEmailAddress is String,
                ) {
                    Icon(
                        imageVector = Mail,
                        contentDescription = stringResource(id = R.string.email_player),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameScreenPreview() {
    CluedoTheme {
        GameBody(
            playerInfoList = (0L until 4).map {
                PlayerInfo(
                    gameId = 0,
                    playerId = it,
                    playerName = "Player $it",
                    playerEmailAddress = "player$it@email.com",
                    targetId = it + 1,
                    targetName = "Player ${it + 1}",
                    placeId = it,
                    placeName = "Place $it",
                    weaponId = it,
                    weaponName = "Weapon $it",
                )
            },
            onPlayerClick = {},
            onKillPlayerClick = {},
            onEmailPlayerClick = {},
        )
    }
}
