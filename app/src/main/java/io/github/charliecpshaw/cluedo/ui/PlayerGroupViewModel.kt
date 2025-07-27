package io.github.charliecpshaw.cluedo.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.repository.PlayerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerGroupViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    val groupId: Long =
        checkNotNull(savedStateHandle[PlayerGroupDestination.GROUP_ID_ARG])

    val playerGroupUiState: StateFlow<PlayerGroupUiState> =
        playerRepository.getPlayerGroupStream(groupId)
            .filterNotNull()
            .map {
                PlayerGroupUiState(groupId = it.id, name = it.name)
            }.combine(
                flow = playerRepository.getAllPlayersInGroupStream(groupId).filterNotNull(),
            ) { uiState, players ->
                uiState.copy(players = players)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlayerGroupUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteGroup() {
        playerRepository.deleteGroup(groupId)
    }
}

data class PlayerGroupUiState(
    val groupId: Long = 0,
    val name: String = "",
    val players: List<Player> = listOf(),
)
