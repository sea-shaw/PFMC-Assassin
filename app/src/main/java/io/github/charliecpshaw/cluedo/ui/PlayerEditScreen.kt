package io.github.charliecpshaw.cluedo.ui

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.ui.navigation.NavigationDestination
import io.github.charliecpshaw.cluedo.R
import kotlinx.coroutines.launch

object PlayerEditDestination : NavigationDestination {
    override val route = "player_edit"
    const val PLAYER_ID_ARG = "player_id"
    val routeWithArgs = "$route/{$PLAYER_ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: PlayerEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var canClickSave by rememberSaveable { mutableStateOf(true) }
    var deleteConformationRequired by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CluedoTopAppBar(
                title = stringResource(id = R.string.player_edit_title),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                hasDeleteButton = true,
                deleteContentDescriptionRes = R.string.player_delete,
                onDeleteClick = { deleteConformationRequired = true }
            )
        }
    ) { innerPadding ->
        PlayerEntryBody(
            playerEntryUiState = viewModel.playerEntryUiState,
            onValueChange = viewModel::updateUiState,
            canClickSave = canClickSave,
            onSaveClick = {
                coroutineScope.launch {
                    canClickSave = false
                    viewModel.savePlayer()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
        if (deleteConformationRequired) {
            DeleteConfirmationDialogue(
                deleteQuestionRes = R.string.player_delete_question,
                onDeleteConfirm = {
                    deleteConformationRequired = false
                    coroutineScope.launch {
                        viewModel.deletePlayer()
                        navigateBack()
                    }
                },
                onDeleteCancel = { deleteConformationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}
