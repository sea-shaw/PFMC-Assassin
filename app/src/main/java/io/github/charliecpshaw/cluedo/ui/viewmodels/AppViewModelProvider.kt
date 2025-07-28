package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.charliecpshaw.cluedo.CluedoApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            GroupsViewModel(
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            GroupEntryViewModel(
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            GroupEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            GroupViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            ComponentEntryViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            ComponentEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
    }
}

fun CreationExtras.cluedoApplication(): CluedoApplication {
    return (this[AndroidViewModelFactory.APPLICATION_KEY] as CluedoApplication)
}
