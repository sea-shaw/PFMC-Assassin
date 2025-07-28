package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.ui.navigation.GroupDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class GroupViewModel<C : Component, G : Group>(
    savedStateHandle: SavedStateHandle,
    private val componentRepository: ComponentRepository<C, G>,
) : ViewModel() {

    val groupId: Long =
        checkNotNull(savedStateHandle[GroupDestination.ID_ARG])

    val uiState: StateFlow<GroupUiState<C>> =
        componentRepository.getGroupStream(groupId)
            .filterNotNull()
            .map {
                GroupUiState<C>(name = it.name)
            }.combine(
                flow = componentRepository.getAllComponentsInGroupStream(groupId).filterNotNull(),
            ) { uiState, components ->
                uiState.copy(components = components)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GroupUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteGroup() {
        componentRepository.deleteGroup(groupId)
    }
}

data class GroupUiState<C : Component>(
    val name: String = "",
    val components: List<C> = listOf(),
)
