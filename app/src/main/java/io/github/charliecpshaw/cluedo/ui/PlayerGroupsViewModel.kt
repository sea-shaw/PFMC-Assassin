package io.github.charliecpshaw.cluedo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerGroupsViewModel(playerRepository: ComponentRepository<Player, PlayerGroup>) : ViewModel() {
    val playerGroupsUiState: StateFlow<PlayerGroupsUiState> =
        playerRepository.getAllGroupsStream()
            .filterNotNull()
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

data class PlayerGroupsUiState(val playerGroups: List<PlayerGroup> = listOf())
