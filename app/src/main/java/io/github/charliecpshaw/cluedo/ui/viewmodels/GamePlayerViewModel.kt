package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.ui.navigation.GamePlayerDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GamePlayerViewModel(
    savedStateHandle: SavedStateHandle,
    gameRepository: GameRepository,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val destination = savedStateHandle.toRoute<GamePlayerDestination>()
    private val gameId = destination.gameId
    private val playerId = destination.playerId

    val uiState: StateFlow<GamePlayerUiState> = gameRepository.getPlayerStream(gameId, playerId)
        .filterNotNull()
        .map { GamePlayerUiState(playerInfo = it) }
        .combine(
            flow = gameRepository.getGameStream(gameId).filterNotNull()
        ) { uiState, game ->
            uiState.copy(gameName = game.name)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GamePlayerUiState()
        )
}

data class GamePlayerUiState(
    val gameName: String = "",
    val playerInfo: PlayerInfo = PlayerInfo(
        gameId = 0,
        playerId = 0,
        playerName = "",
        playerEmailAddress = null,
        targetId = 0,
        targetName = "",
        placeId = 0,
        placeName = "",
        weaponId = 0,
        weaponName = "",
    ),
)
