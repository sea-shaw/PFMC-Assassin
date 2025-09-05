package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class ComponentEditViewModel<C : Component, G : Group>(
    private val id: Long,
    private val componentRepository: ComponentRepository<C, G>,
) : ViewModel() {

    companion object {
        private fun isValidInput(details: ComponentDetails): Boolean {
            return details.name.isNotBlank()
        }
    }

    var uiState by mutableStateOf(ComponentEntryUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = componentRepository
                .getComponentStream(id)
                .filterNotNull()
                .first()
                .toEntryUiState(isValidInput = true)
        }
    }

    fun updateUiState(details: ComponentDetails) {
        uiState = ComponentEntryUiState(
            details = details,
            isValidInput = isValidInput(details)
        )
    }

    suspend fun saveComponent() {
        with(uiState.details) {
            if (isValidInput(this)) {
                componentRepository.updateComponent(id, name, isActive)
            }
        }
    }

    suspend fun deleteComponent(): Boolean {
        return componentRepository.deleteComponent(id) > 0
    }
}
