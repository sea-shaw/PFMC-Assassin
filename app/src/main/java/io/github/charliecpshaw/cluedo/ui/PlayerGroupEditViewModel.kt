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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlayerGroupEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: ComponentRepository<Player, PlayerGroup>,
) : ViewModel() {

    private val groupId: Long =
        checkNotNull(savedStateHandle[PlayerGroupEditDestination.GROUP_ID_ARG])

    var playerGroupEntryUiState by mutableStateOf(PlayerGroupEntryUiState())
        private set

    init {
        viewModelScope.launch {
            playerGroupEntryUiState = playerRepository
                .getGroupStream(groupId)
                .filterNotNull()
                .map {
                    PlayerGroupEntryUiState(
                        name = it.name,
                        isEntryValid = true,
                    )
                }
                .first()
        }
    }

    fun updateUiState(name: String) {
        playerGroupEntryUiState = PlayerGroupEntryUiState(name = name, isEntryValid = isValidInput(name))
    }

    suspend fun savePlayerGroup() {
        if (isValidInput()) {
            playerRepository.updateGroup(id = groupId, name = playerGroupEntryUiState.name)
        }
    }

    private fun isValidInput(name: String = playerGroupEntryUiState.name): Boolean {
        return name.isNotBlank()
    }
}
