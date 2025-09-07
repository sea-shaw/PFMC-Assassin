package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Game
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GamesViewModel(
    gameRepository: GameRepository,
) : ViewModel() {
    val uiState: StateFlow<GamesUiState> = gameRepository.getAllGamesStream()
        .filterNotNull()
        .map { GamesUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GamesUiState(),
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GamesUiState(
    val games: List<Game> = listOf(),
)
