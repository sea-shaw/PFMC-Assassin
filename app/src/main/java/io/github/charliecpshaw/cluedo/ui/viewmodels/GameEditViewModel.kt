package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.ui.navigation.GameEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GameEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {
    companion object {
        private fun isValidInput(name: String): Boolean {
            return name.isNotBlank()
        }
    }

    private val gameId: Long = savedStateHandle.toRoute<GameEditDestination>().id

    var uiState by mutableStateOf(GameEntryUiState())

    init {
        viewModelScope.launch {
            uiState = gameRepository
                .getGameStream(gameId)
                .filterNotNull()
                .map {
                    GameEntryUiState(
                        name = it.name,
                        isValidInput = true,
                    )
                }
                .first()
        }
    }

    fun updateUiState(name: String) {
        uiState = GameEntryUiState(name = name, isValidInput = isValidInput(name))
    }

    suspend fun saveGame() {
        with(uiState) {
            if (isValidInput(name)) {
                gameRepository.updateGameName(gameId, name)
            }
        }
    }

    fun shuffleGame() = viewModelScope.launch {
        gameRepository.shuffleGame(gameId)
    }
}
