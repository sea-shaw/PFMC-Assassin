package io.github.charliecpshaw.cluedo.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.ui.navigation.GroupDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerGroupViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: ComponentRepository<Player, PlayerGroup>,
) : ViewModel() {

    val groupId: Long =
        checkNotNull(savedStateHandle[GroupDestination.ID_ARG])

    val playerGroupUiState: StateFlow<PlayerGroupUiState> =
        playerRepository.getGroupStream(groupId)
            .filterNotNull()
            .map {
                PlayerGroupUiState(groupId = it.id, name = it.name)
            }.combine(
                flow = playerRepository.getAllComponentsInGroupStream(groupId).filterNotNull(),
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
