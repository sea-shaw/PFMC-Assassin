package io.github.charliecpshaw.cluedo.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.ui.icons.Shuffle
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GameEditViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameEditScreen(
  navigateBack: () -> Unit,
  onNavigateUp: () -> Unit,
  viewModel: GameEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
  val coroutineScope = rememberCoroutineScope()
  var canClickSave by rememberSaveable { mutableStateOf(true) }

  Scaffold(
    topBar = {
      CluedoTopAppBar(
        title = stringResource(R.string.game_edit_title),
        canNavigateBack = true,
        navigateUp = onNavigateUp,
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          viewModel.shuffleGame()
          navigateBack()
        },
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
          .padding(
            end = WindowInsets.safeDrawing.asPaddingValues()
              .calculateEndPadding(LocalLayoutDirection.current)
          ),
      ) {
        Icon(
          imageVector = Shuffle,
          contentDescription = stringResource(R.string.shuffle_game),
        )
      }
    },
  ) { innerPadding ->
    NameEntryBody(
      uiState = viewModel.uiState,
      onNameValueChange = viewModel::updateUiState,
      saveActionResId = R.string.save_action,
      canClickSave = canClickSave,
      onSaveClick = {
        coroutineScope.launch {
          canClickSave = false
          viewModel.saveGame()
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
  }
}
