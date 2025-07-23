package io.github.charliecpshaw.cluedo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.ui.navigation.NavigationDestination
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme

object PlayerGroupsDestination : NavigationDestination {
    override val route = "player_groups"
    override val titleRes = R.string.player_group_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerGroupsScreen(
    navigateToPlayerEntry: () -> Unit,
    navigateToPlayerEdit: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerGroupsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val playerGroupsUiState by viewModel.playerGroupsUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CluedoTopAppBar(
                title = stringResource(PlayerGroupsDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPlayerEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.player_entry_title)
                )
            }
        },
    ) { innerPadding ->
        PlayerGroupsBody(
            playerGroupList = playerGroupsUiState.playerGroupsList,
            onPlayerGroupClick = navigateToPlayerEdit,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun PlayerGroupsBody(
    playerGroupList: List<PlayerGroup>,
    onPlayerGroupClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PlayerGroupsList(
            playerGroupList = playerGroupList,
            onPlayerGroupClick = { onPlayerGroupClick(it.id) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun PlayerGroupsList(
    playerGroupList: List<PlayerGroup>,
    onPlayerGroupClick: (PlayerGroup) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(
            items = playerGroupList,
            key = { it.id },
        ) { playerGroup ->
            PlayerGroupItem(
                playerGroup = playerGroup,
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onPlayerGroupClick(playerGroup) }
            )
        }
    }
}

@Composable
private fun PlayerGroupItem(
    playerGroup: PlayerGroup,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = playerGroup.name,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerGroupsBodyPreview() {
    CluedoTheme {
        PlayerGroupsBody(
            listOf(
                PlayerGroup(id = 0, name = "PFMC"),
                PlayerGroup(id = 1, name = "PFMC+"),
            ),
            onPlayerGroupClick = {},
        )
    }
}
