package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.ui.navigation.Destination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GamePlayerViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val gameId: Long = checkNotNull(savedStateHandle[Destination.GamePlayer.GAME_ID_ARG])
    private val playerId: Long = checkNotNull(savedStateHandle[Destination.GamePlayer.PLAYER_ID_ARG])

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

    fun killTarget() = viewModelScope.launch {
        gameRepository.killTarget(gameId, playerId)
    }
}

data class GamePlayerUiState(
    val gameName: String = "",
    val playerInfo: PlayerInfo = PlayerInfo(
        gameId = 0,
        playerId = 0,
        playerName = "",
        targetId = 0,
        targetName = "",
        placeId = 0,
        placeName = "",
        weaponId = 0,
        weaponName = "",
    ),
)
