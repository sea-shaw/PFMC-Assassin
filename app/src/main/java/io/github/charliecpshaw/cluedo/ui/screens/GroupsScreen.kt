package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GroupsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified V : GroupsViewModel<C, G>, C : Component, G : Group<C>> GroupsScreen(
  @StringRes titleResId: Int,
  noinline navigateToGroupEntry: () -> Unit,
  noinline navigateToGroup: (Long) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: V = viewModel(factory = AppViewModelProvider.Factory),
) {
  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      CluedoTopAppBar(
        title = stringResource(titleResId),
        canNavigateBack = false,
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = navigateToGroupEntry,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
          .padding(
            end = WindowInsets.safeDrawing.asPaddingValues()
              .calculateEndPadding(LocalLayoutDirection.current)
          )
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = stringResource(R.string.player_group_entry_title)
        )
      }
    },
  ) { innerPadding ->
    GroupsBody(
      groupList = uiState.groups,
      onGroupClick = navigateToGroup,
      modifier = modifier.fillMaxSize(),
      contentPadding = innerPadding,
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun GroupsBodyPreview() {
  CluedoTheme {
    GroupsBody(
      groupList = listOf(
        PlayerGroup(id = 0, name = "PFMC"),
        PlayerGroup(id = 1, name = "PFMC+"),
      ),
      onGroupClick = {},
    )
  }
}
