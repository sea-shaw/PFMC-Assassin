package io.github.charliecpshaw.cluedo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.repository.PlayerRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlayerEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    private val playerId: Long =
        checkNotNull(savedStateHandle[PlayerEditDestination.PLAYER_ID_ARG])

    var playerEntryUiState by mutableStateOf(PlayerEntryUiState())
        private set

    init {
        viewModelScope.launch {
            playerEntryUiState = playerRepository
                .getPlayerStream(playerId)
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
            val player = playerEntryUiState.details.toPlayer()
            playerRepository.updatePlayer(player)
        }
    }

    suspend fun deletePlayer() {
        
    }

    private fun isValidInput(playerDetails: PlayerDetails = playerEntryUiState.details): Boolean {
        return playerDetails.name.isNotBlank()
    }
}
