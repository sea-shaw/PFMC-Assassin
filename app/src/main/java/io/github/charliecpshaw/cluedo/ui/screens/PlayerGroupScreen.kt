package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GroupViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerGroupScreen(
    navigateToEdit: (Long) -> Unit,
    navigateToPlayerEdit: (Long) -> Unit,
    navigateToPlayerEntry: (Long) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GroupViewModel<Player, PlayerGroup> = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    var deleteConformationRequired by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CluedoTopAppBar(
                title = uiState.name,
                canNavigateBack = true,
                navigateUp = navigateBack,
                hasEditButton = true,
                onEditClick = { navigateToEdit(viewModel.groupId) },
                editContentDescriptionRes = R.string.player_group_edit_title,
                hasDeleteButton = true,
                onDeleteClick = { deleteConformationRequired = true },
                deleteContentDescriptionRes = R.string.player_group_delete
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToPlayerEntry(viewModel.groupId) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.player_group_edit_title),
                )
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        PlayerGroupBody(
            playerList = uiState.components,
            onPlayerClick = navigateToPlayerEdit,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
        if (deleteConformationRequired) {
            DeleteConfirmationDialogue(
                deleteQuestionRes = R.string.player_group_delete_question,
                onDeleteConfirm = {
                    deleteConformationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteGroup()
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
fun PlayerGroupBody(
    playerList: List<Player>,
    onPlayerClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PlayerList(
            playerList = playerList,
            contentPadding = contentPadding,
            onPlayerClick = onPlayerClick,
            modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
        )
    }
}

@Composable
fun PlayerList(
    playerList: List<Player>,
    contentPadding: PaddingValues,
    onPlayerClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        if (playerList.isNotEmpty()) {
            items(
                items = playerList,
                key = { it.id },
            ) { player ->
                PlayerItem(
                    player = player,
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onPlayerClick(player.id) },
                )
            }
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (player.isActive) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.active),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.inactive),
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialogue(
    @StringRes deleteQuestionRes: Int,
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDeleteCancel) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = deleteQuestionRes),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = onDeleteCancel,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = onDeleteConfirm,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerGroupBodyPreview() {
    CluedoTheme {
        PlayerGroupBody(
            playerList = listOf(
                Player(id = 0, name = "Player 0", isActive = true, groupId = 0),
                Player(id = 1, name = "Player 1", isActive = false, groupId = 0),
            ),
            onPlayerClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteConfirmationDialoguePreview() {
    CluedoTheme {
        DeleteConfirmationDialogue(
            deleteQuestionRes = R.string.player_group_delete_question,
            onDeleteConfirm = {},
            onDeleteCancel = {},
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}
