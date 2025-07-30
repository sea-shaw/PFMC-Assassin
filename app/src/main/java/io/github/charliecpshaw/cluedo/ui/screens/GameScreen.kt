package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GameViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPlayer: (Long, Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConformationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CluedoTopAppBar(
                title = uiState.name,
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                hasDeleteButton = true,
                onDeleteClick = { deleteConformationRequired = true },
                deleteContentDescriptionResId = R.string.delete_game
            )
        },
    ) { innerPadding ->
        GameBody(
            playerInfoList = uiState.players,
            onPlayerClick = navigateToPlayer,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
        if (deleteConformationRequired) {
            DeleteConfirmationDialogue(
                deleteQuestionResId = R.string.game_delete_question,
                onDeleteConfirm = {
                    deleteConformationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteGame()
                        navigateBack()
                    }
                },
                onDeleteCancel = { deleteConformationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun GameBody(
    playerInfoList: List<PlayerInfo>,
    onPlayerClick: (Long, Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PlayerInfoList(
            playerInfoList = playerInfoList,
            onGameClick = { onPlayerClick(it.gameId, it.playerId) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun PlayerInfoList(
    playerInfoList: List<PlayerInfo>,
    onGameClick: (PlayerInfo) -> Unit,
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
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onGameClick(playerInfo) }
                )
            }
        }
    }
}

@Composable
private fun PlayerInfoCard(
    playerInfo: PlayerInfo,
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
                )
            }
            PlayerInfoDetailRow(
                detailNameResId = R.string.target_name,
                detailValue = playerInfo.targetName,
                modifier = Modifier.fillMaxWidth(),
            )
            PlayerInfoDetailRow(
                detailNameResId = R.string.place_name,
                detailValue = playerInfo.placeName,
                modifier = Modifier.fillMaxWidth(),
            )
            PlayerInfoDetailRow(
                detailNameResId = R.string.weapon_name,
                detailValue = playerInfo.weaponName,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PlayerInfoDetailRow(
    @StringRes detailNameResId: Int,
    detailValue: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(detailNameResId),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = detailValue,
            style = MaterialTheme.typography.bodyLarge
        )
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
                    isAlive = true,
                    targetId = it + 1,
                    targetName = "Player ${it + 1}",
                    placeId = it,
                    placeName = "Place $it",
                    weaponId = it,
                    weaponName = "Weapon $it",
                )
            },
            onPlayerClick = {_, _, -> },
        )
    }
}
