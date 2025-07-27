package io.github.charliecpshaw.cluedo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlayerEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: ComponentRepository<Player, PlayerGroup>,
) : ViewModel() {

    private val playerId: Long =
        checkNotNull(savedStateHandle[PlayerEditDestination.PLAYER_ID_ARG])

    var playerEntryUiState by mutableStateOf(PlayerEntryUiState())
        private set

    init {
        viewModelScope.launch {
            playerEntryUiState = playerRepository
                .getComponentStream(playerId)
                .filterNotNull()
                .first()
                .toPlayerEntryUiState(true)
        }
    }

    fun updateUiState(playerDetails: PlayerDetails) {
        playerEntryUiState = PlayerEntryUiState(
            details = playerDetails,
            isValidInput = isValidInput(playerDetails),
        )
    }

    suspend fun savePlayer() {
        if (isValidInput()) {
            with (playerEntryUiState.details) {
                playerRepository.updateComponent(playerId, name, isActive)
            }
        }
    }

    suspend fun deletePlayer() {
        playerRepository.deleteComponent(playerId)
    }

    private fun isValidInput(playerDetails: PlayerDetails = playerEntryUiState.details): Boolean {
        return playerDetails.name.isNotBlank()
    }
}
