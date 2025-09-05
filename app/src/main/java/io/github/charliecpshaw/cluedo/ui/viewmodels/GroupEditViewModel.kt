package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class GroupEditViewModel<C : Component, G : Group>(
    private val id: Long,
    private val componentRepository: ComponentRepository<C, G>,
) : ViewModel() {

    companion object {
        private fun isValidInput(name: String): Boolean {
            return name.isNotBlank()
        }
    }

    var uiState by mutableStateOf(GroupEntryUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = componentRepository
                .getGroupStream(id)
                .filterNotNull()
                .map {
                    GroupEntryUiState(
                        name = it.name,
                        isValidInput = true,
                    )
                }
                .first()
        }
    }

    fun updateUiState(name: String) {
        uiState = GroupEntryUiState(name = name, isValidInput = isValidInput(name))
    }

    suspend fun saveGroup() {
        with(uiState) {
            if (isValidInput(name)) {
                componentRepository.updateGroup(id = id, name = name)
            }
        }
    }
}
