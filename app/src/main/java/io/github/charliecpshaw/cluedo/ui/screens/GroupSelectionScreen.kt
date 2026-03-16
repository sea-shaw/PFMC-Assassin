package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GroupSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified V : GroupSelectionViewModel<C, G>, C : Component, G : Group<C>> GroupSelectionScreen(
  @StringRes titleResId: Int,
  noinline onGroupClick: (Long) -> Unit,
  noinline onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: V = viewModel(factory = AppViewModelProvider.Factory),
) {
  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      CluedoTopAppBar(
        title = stringResource(titleResId),
        canNavigateBack = true,
        navigateUp = onNavigateUp,
      )
    },
  ) { innerPadding ->
    GroupsBody(
      groupList = uiState.groups,
      onGroupClick = onGroupClick,
      modifier = modifier.fillMaxSize(),
      contentPadding = innerPadding,
    )
  }
}
