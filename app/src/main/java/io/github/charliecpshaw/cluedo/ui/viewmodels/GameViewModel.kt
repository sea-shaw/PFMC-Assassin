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

class GameViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val gameId: Long = checkNotNull(savedStateHandle[Destination.Game.GAME_ID_ARG])

    var uiState: StateFlow<GameUiState> = gameRepository.getGameStream(gameId)
        .filterNotNull()
        .map { GameUiState(name = it.name) }
        .combine(
            flow = gameRepository.getAllAlivePlayersInGameStream(gameId),
        ) { uiState, players ->
            uiState.copy(players = players)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GameUiState(),
        )

    suspend fun deleteGame() {
        gameRepository.deleteGame(gameId)
    }
}

data class GameUiState(
    val name: String = "",
    val players: List<PlayerInfo> = listOf(),
)
