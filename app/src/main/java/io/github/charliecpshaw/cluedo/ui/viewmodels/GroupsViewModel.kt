package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class GroupsViewModel<C : Component, G : Group<C>>(
    componentRepository: ComponentRepository<C, G>,
) : ViewModel() {
    val uiState: StateFlow<GroupsUiState<G>> = componentRepository.getAllGroupsStream()
        .filterNotNull()
        .map { GroupsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GroupsUiState(),
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GroupsUiState<G : Group<*>>(
    val groups: List<G> = listOf(),
)
