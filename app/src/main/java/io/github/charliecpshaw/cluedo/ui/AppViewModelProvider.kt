package io.github.charliecpshaw.cluedo.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.charliecpshaw.cluedo.CluedoApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            PlayerGroupsViewModel(
                playerRepository =  cluedoApplication().container.playerRepository,
            )
        }
        initializer {
//            PlayerGroupEntryViewModel(
//                playerRepository = cluedoApplication().container.playerRepository,
//            )
            GroupEntryViewModel(
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
//            PlayerGroupEditViewModel(
//                savedStateHandle = this.createSavedStateHandle(),
//                playerRepository = cluedoApplication().container.playerRepository,
//            )
            GroupEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                componentRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            PlayerGroupViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                playerRepository = cluedoApplication().container.playerRepository,
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
