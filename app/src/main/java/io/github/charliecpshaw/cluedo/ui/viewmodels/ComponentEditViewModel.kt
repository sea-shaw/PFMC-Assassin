package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class ComponentEditViewModel<C : Component, G : Group<C>, D : ComponentDetails<C, D>>(
    private val id: Long,
    private val componentRepository: ComponentRepository<C, G>,
    private val detailsFactory: ComponentDetailsFactory<C, D>
) : ViewModel() {

    var uiState by mutableStateOf(
        value = ComponentEntryUiState(
            details = detailsFactory.defaultDetails(),
            isValidInput = false
        ),
    )
        private set

    init {
        viewModelScope.launch {
            uiState = componentRepository
                .getComponentStream(id)
                .filterNotNull()
                .first()
                .toEntryUiState(detailsFactory = detailsFactory, isValidInput = true)
        }
    }

    fun updateUiState(details: D) {
        uiState = ComponentEntryUiState(
            details = details,
            isValidInput = details.isValid()
        )
    }

    suspend fun saveComponent() {
        with(uiState.details) {
            if (isValid()) {
                val component = toComponent(id, 0)
                componentRepository.updateComponent(component)
            }
        }
    }

    suspend fun deleteComponent(): Boolean {
        return componentRepository.deleteComponent(id) > 0
    }
}
