package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

abstract class ComponentEntryViewModel<C : Component, G : Group>(
    private val groupId: Long,
    private val componentRepository: ComponentRepository<C, G>,
) : ViewModel() {

    companion object {
        private fun isValidInput(details: ComponentDetails): Boolean {
            return details.name.isNotBlank()
        }
    }

    var uiState by mutableStateOf(ComponentEntryUiState())
        private set

    fun updateUiState(details: ComponentDetails) {
        uiState = ComponentEntryUiState(
            details = details,
            isValidInput = isValidInput(details)
        )
    }

    suspend fun saveComponent() {
        with(uiState.details) {
            if (isValidInput(this)) {
                componentRepository.insertComponent(name, groupId, isActive)
            }
        }
    }
}

data class ComponentEntryUiState(
    val details: ComponentDetails = ComponentDetails(),
    val isValidInput: Boolean = false,
)

data class ComponentDetails(
    val name: String = "",
    val isActive: Boolean = true,
)

fun Component.toEntryUiState(isValidInput: Boolean): ComponentEntryUiState {
    return with(this) {
        ComponentEntryUiState(
            details = toDetails(),
            isValidInput = isValidInput,
        )
    }
}

fun Component.toDetails(): ComponentDetails {
    return with(this) {
        ComponentDetails(
            name = name,
            isActive = isActive,
        )
    }
}
