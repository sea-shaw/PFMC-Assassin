package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import io.github.charliecpshaw.cluedo.data.model.Game
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GamesViewModel
import java.time.Instant
import java.time.Month
import java.time.ZoneId
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(
    navigateToGameEntry: () -> Unit,
    navigateToGame: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GamesViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CluedoTopAppBar(
                title = stringResource(R.string.games_title),
                canNavigateBack = false,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToGameEntry,
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
        GamesBody(
            gameList = uiState.games,
            onGameClick = navigateToGame,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
fun GamesBody(
    gameList: List<Game>,
    onGameClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        GamesList(
            gameList = gameList,
            onGameClick = { onGameClick(it.id) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun GamesList(
    gameList: List<Game>,
    onGameClick: (Game) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        if (gameList.isNotEmpty()) {
            items(
                items = gameList,
                key = { it.id },
            ) { game ->
                GameCard(
                    game = game,
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onGameClick(game) }
                )
            }
        }
    }
}

@Composable
private fun GameCard(
    game: Game,
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
                    text = game.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (game.end == null) {
                        game.start.getDateString()
                    } else {
                        "${game.start.getDateString()} - ${game.end.getDateString()}"
                    }
                )
            }
        }
    }
}

private fun Instant.getDateString(): String {
    val zoneOffset = getZoneOffset()
    val offsetDateTime = atOffset(zoneOffset)
    val year = offsetDateTime.year
    val month = offsetDateTime.month
    val day = offsetDateTime.dayOfMonth

    return "${day.toOrdinal()} ${month.toDisplay()} $year"
}

private fun getZoneOffset(): ZoneOffset {
    return ZoneId.systemDefault().rules.getOffset(Instant.now())
}

private fun Int.toOrdinal(): String {
    val numString = toString()
    return when (numString.last()) {
        '1' -> "${numString}st"
        '2' -> "${numString}nd"
        '3' -> "${numString}rd"
        else -> "${numString}th"
    }
}

private fun Month.toDisplay(): String {
    val monthString = toString()
    return "${monthString[0].uppercase()}${monthString.drop(1).lowercase()}"
}

@Preview(showBackground = true)
@Composable
private fun GamesBodyPreview() {
    CluedoTheme {
        GamesBody(
            gameList = listOf(
                Game(
                    id = 0,
                    name = "PFMC",
                    start = Instant.parse("2025-07-27T00:00:00Z"),
                    end = Instant.parse("2025-08-02T00:00:00Z"),
                    playerGroupId = 0,
                    placeGroupId = 0,
                    weaponGroupId = 0,
                ),
                Game(
                    id = 1,
                    name = "PFMC+",
                    start = Instant.parse("2025-08-05T00:00:00Z"),
                    end = null,
                    playerGroupId = 0,
                    placeGroupId = 0,
                    weaponGroupId = 0,
                ),
            ),
            onGameClick = {},
        )
    }
}
