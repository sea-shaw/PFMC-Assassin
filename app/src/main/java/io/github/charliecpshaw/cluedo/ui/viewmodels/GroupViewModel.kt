package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class GroupViewModel<C : Component, G : Group<C>>(
    val groupId: Long,
    private val componentRepository: ComponentRepository<C, G>,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

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

    suspend fun deleteGroup() {
        componentRepository.deleteGroup(groupId)
    }
}

data class GroupUiState<C : Component>(
    val name: String = "",
    val components: List<C> = listOf(),
)
