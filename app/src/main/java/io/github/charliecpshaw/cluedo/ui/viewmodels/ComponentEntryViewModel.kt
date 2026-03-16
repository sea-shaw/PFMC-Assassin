package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

abstract class ComponentEntryViewModel<C : Component, G : Group<C>, D : ComponentDetails<C, D>>(
  private val groupId: Long,
  private val componentRepository: ComponentRepository<C, G>,
  detailsFactory: ComponentDetailsFactory<C, D>
) : ViewModel() {

  var uiState by mutableStateOf(
    ComponentEntryUiState(
      details = detailsFactory.defaultDetails(),
      isValidInput = false,
    )
  )
  private set

  fun updateUiState(details: D) {
    uiState = ComponentEntryUiState(
      details = details,
      isValidInput = details.isValid(),
    )
  }

  suspend fun saveComponent() {
    with(uiState.details) {
      if (isValid()) {
        val component = toComponent(0, groupId)
        componentRepository.insertComponent(component)
      }
    }
  }
}

data class ComponentEntryUiState<C : Component, D : ComponentDetails<C, D>>(
  val details: D,
  val isValidInput: Boolean,
)

fun <C : Component, D : ComponentDetails<C, D>> C.toEntryUiState(
  detailsFactory: ComponentDetailsFactory<C, D>,
  isValidInput: Boolean,
): ComponentEntryUiState<C, D> {
  return ComponentEntryUiState(
    details = detailsFactory.toDetails(this),
    isValidInput = isValidInput,
  )
}
