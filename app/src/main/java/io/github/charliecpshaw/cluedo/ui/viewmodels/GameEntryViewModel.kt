package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.ui.navigation.GameEntryDestination

class GameEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {

    companion object {
        private fun isValidInput(name: String): Boolean {
            return name.isNotBlank()
        }
    }

    private val playerGroupId: Long =
        checkNotNull(savedStateHandle[GameEntryDestination.PLAYER_GROUP_ID_ARG])

    private val placeGroupId: Long =
        checkNotNull(savedStateHandle[GameEntryDestination.PLACE_GROUP_ID_ARG])

    private val weaponGroupId: Long =
        checkNotNull(savedStateHandle[GameEntryDestination.WEAPON_GROUP_ID_ARG])

    var uiState by mutableStateOf(GameEntryUiState())
        private set

    fun updateUiState(name: String) {
        uiState = uiState.copy(
            name = name,
            isValidInput = isValidInput(name)
        )
    }

    suspend fun createGame() {
        if (isValidInput(uiState.name)) {
            gameRepository.createGame(
                name = uiState.name,
                playerGroupId = playerGroupId,
                placeGroupId = placeGroupId,
                weaponGroupId = weaponGroupId,
            )
        }
    }
}

data class GameEntryUiState(
    override val name: String = "",
    override val isValidInput: Boolean = false,
) : NameEntryUiState
