package io.github.charliecpshaw.cluedo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.PlayerGroup
import io.github.charliecpshaw.cluedo.data.PlayerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerGroupsViewModel(playerRepository: PlayerRepository) : ViewModel() {
    val playerGroupsUiState: StateFlow<PlayerGroupsUiState> =
        playerRepository.getAllPlayerGroupsStream()
            .map { PlayerGroupsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlayerGroupsUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class PlayerGroupsUiState(val playerGroupsList: List<PlayerGroup> = listOf())