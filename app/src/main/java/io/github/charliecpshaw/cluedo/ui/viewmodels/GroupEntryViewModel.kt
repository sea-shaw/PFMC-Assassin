package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

abstract class GroupEntryViewModel<C : Component, G : Group<C>>(
  private val componentRepository: ComponentRepository<C, G>,
) : ViewModel() {
  companion object {
    private fun isValidInput(name: String): Boolean {
      return name.isNotBlank()
    }
  }

  var uiState by mutableStateOf(GroupEntryUiState())
    private set

  fun updateUiState(name: String) {
    uiState = GroupEntryUiState(name = name, isValidInput = isValidInput(name))
  }

  suspend fun saveGroup() {
    if (isValidInput(uiState.name)) {
      componentRepository.insertGroup(uiState.name)
    }
  }
}

data class GroupEntryUiState(
  override val name: String = "",
  override val isValidInput: Boolean = false,
) : NameEntryUiState
