package io.github.charliecpshaw.cluedo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.repository.PlayerRepository

class PlayerEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    private val groupId: Long =
        checkNotNull(savedStateHandle[PlayerEntryDestination.GROUP_ID_ARG])

    var playerEntryUiState by mutableStateOf(PlayerEntryUiState())
        private set

    fun updateUiState(playerDetails: PlayerDetails) {
        playerEntryUiState = PlayerEntryUiState(
            details = playerDetails,
            isValidInput = isValidInput(playerDetails),
        )
    }

    suspend fun savePlayer() {
        val player = Player(
            name = playerEntryUiState.details.name,
            isActive = playerEntryUiState.details.isActive,
            groupId = groupId,
        )
        playerRepository.insertPlayer(player)
    }

    private fun isValidInput(playerDetails: PlayerDetails = playerEntryUiState.details): Boolean {
        return playerDetails.name.isNotBlank()
    }
}

data class PlayerEntryUiState(
    val details: PlayerDetails = PlayerDetails(),
    val isValidInput: Boolean = true,
)

data class PlayerDetails(
    val name: String = "",
    val isActive: Boolean = true,
)