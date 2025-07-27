package io.github.charliecpshaw.cluedo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

class PlayerGroupEntryViewModel(
    private val playerRepository: ComponentRepository<Player, PlayerGroup>,
) : ViewModel() {
    var playerGroupEntryUiState by mutableStateOf(PlayerGroupEntryUiState())
        private set

    fun updateUiState(name: String) {
        playerGroupEntryUiState = PlayerGroupEntryUiState(name = name, isEntryValid = isValidInput(name))
    }

    suspend fun savePlayerGroup() {
        if (isValidInput()) {
            playerRepository.insertGroup(name = playerGroupEntryUiState.name)
        }
    }

    private fun isValidInput(name: String = playerGroupEntryUiState.name): Boolean {
        return name.isNotBlank()
    }
}

data class PlayerGroupEntryUiState(
    val name: String = "",
    val isEntryValid: Boolean = false,
)
