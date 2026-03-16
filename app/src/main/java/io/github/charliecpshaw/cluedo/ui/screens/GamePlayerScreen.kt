package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GamePlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePlayerScreen(
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: GamePlayerViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier,
    topBar = {
      CluedoTopAppBar(
        title = uiState.gameName,
        canNavigateBack = true,
        navigateUp = onNavigateUp,
      )
    }
  ) { innerPadding ->
    GamePlayerBody(
      playerInfo = uiState.playerInfo,
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

@Composable
private fun GamePlayerBody(
  playerInfo: PlayerInfo,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
  ) {
    PlayerInfoCard(
      playerInfo = playerInfo,
    )
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
        detailNameResId = R.string.person,
        detailValue = playerInfo.targetName,
        modifier = Modifier.fillMaxWidth(),
      )
      PlayerInfoDetailRow(
        detailNameResId = R.string.place,
        detailValue = playerInfo.placeName,
        modifier = Modifier.fillMaxWidth(),
      )
      PlayerInfoDetailRow(
        detailNameResId = R.string.weapon,
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
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.weight(1f))
    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_small)))
    Text(
      text = detailValue,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.End,
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun GamePlayerBodyPreview() {
  CluedoTheme {
    GamePlayerBody(
      playerInfo = PlayerInfo(
        gameId = 0,
        playerId = 0,
        playerName = "Player 0",
        playerEmailAddress = "player0@email.com",
        targetId = 1,
        targetName = "Player 1",
        placeId = 0,
        placeName = "Place 0",
        weaponId = 0,
        weaponName = "Very long weapon name to test spacing",
      ),
    )
  }
}
