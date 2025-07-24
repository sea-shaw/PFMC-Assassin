package io.github.charliecpshaw.cluedo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.repository.PlayerRepository

class PlayerGroupEntryViewModel(
    private val playerRepository: PlayerRepository,
) : ViewModel() {
    var playerGroupUiState by mutableStateOf(PlayerGroupUiState())
        private set

    fun updateUiState(name: String) {
        playerGroupUiState = PlayerGroupUiState(name = name, isEntryValid = validateInput(name))
    }

    suspend fun savePlayerGroup() {
        if (validateInput()) {
            playerRepository.insertGroup(name = playerGroupUiState.name)
        }
    }

    private fun validateInput(name: String = playerGroupUiState.name): Boolean {
        return name.isNotBlank()
    }
}

data class PlayerGroupUiState(
    val name: String = "",
    val isEntryValid: Boolean = false,
)
