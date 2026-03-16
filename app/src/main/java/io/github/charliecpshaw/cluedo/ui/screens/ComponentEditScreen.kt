package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.ComponentDetails
import io.github.charliecpshaw.cluedo.ui.viewmodels.ComponentEditViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified V : ComponentEditViewModel<C, G, D>, C : Component, G : Group<C>, D : ComponentDetails<C, D>> ComponentEditScreen(
  @StringRes titleResId: Int,
  @StringRes deleteContentDescriptionResId: Int,
  @StringRes deleteQuestionResId: Int,
  @StringRes deleteFailedMsgResId: Int,
  crossinline navigateBack: () -> Unit,
  noinline onNavigateUp: () -> Unit,
  extraFields: List<ComponentTextFieldDetails<C, D>> = listOf(),
  canNavigateBack: Boolean = true,
  viewModel: V = viewModel(factory = AppViewModelProvider.Factory),
) {
  val coroutineScope = rememberCoroutineScope()
  var canClickSave by rememberSaveable { mutableStateOf(true) }
  var deleteConformationRequired by rememberSaveable { mutableStateOf(false) }
  var deleteFailed by rememberSaveable { mutableStateOf(false) }
  Scaffold(
    topBar = {
      CluedoTopAppBar(
        title = stringResource(titleResId),
        canNavigateBack = canNavigateBack,
        navigateUp = onNavigateUp,
        hasDeleteButton = true,
        deleteContentDescriptionResId = deleteContentDescriptionResId,
        onDeleteClick = { deleteConformationRequired = true }
      )
    }
  ) { innerPadding ->
    ComponentEntryBody(
      uiState = viewModel.uiState,
      onValueChange = viewModel::updateUiState,
      canClickSave = canClickSave,
      onSaveClick = {
        coroutineScope.launch {
          canClickSave = false
          viewModel.saveComponent()
          navigateBack()
        }
      },
      extraFields = extraFields,
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
      ConfirmationDialogue(
        dialogueText = stringResource(id = deleteQuestionResId),
        confirmTextResId = R.string.delete,
        cancelTextResId = R.string.cancel,
        onConfirm = {
          deleteConformationRequired = false
          coroutineScope.launch {
            deleteFailed = !viewModel.deleteComponent()
            if (!deleteFailed) {
              navigateBack()
            }
          }
        },
        onCancel = { deleteConformationRequired = false },
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
      )
    }
    if (deleteFailed) {
      Dialog(
        onDismissRequest = { deleteFailed = false }
      ) {
        Card(
          modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
          shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
        ) {
          Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Text(
              text = stringResource(id = deleteFailedMsgResId),
              modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End,
            ) {
              TextButton(
                onClick = { deleteFailed = false },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
              ) {
                Text(text = stringResource(id = R.string.ok))
              }
            }
          }
        }
      }
    }
  }
}
