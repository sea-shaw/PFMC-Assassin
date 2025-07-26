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
            PlayerGroupEntryViewModel(
                playerRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            PlayerGroupViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                playerRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            PlayerEntryViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                playerRepository = cluedoApplication().container.playerRepository,
            )
        }
        initializer {
            PlayerEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                playerRepository = cluedoApplication().container.playerRepository,
            )
        }
    }
}

fun CreationExtras.cluedoApplication(): CluedoApplication {
    return (this[AndroidViewModelFactory.APPLICATION_KEY] as CluedoApplication)
}
