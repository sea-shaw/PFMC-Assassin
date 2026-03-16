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
      PlayerGroupsViewModel(
        componentRepository = cluedoApplication().container.playerRepository,
      )
    }
    initializer {
      PlayerGroupEntryViewModel(
        componentRepository = cluedoApplication().container.playerRepository,
      )
    }
    initializer {
      PlayerGroupEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.playerRepository,
      )
    }
    initializer {
      PlayerGroupViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.playerRepository,
      )
    }
    initializer {
      PlayerEntryViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.playerRepository,
      )
    }
    initializer {
      PlayerEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.playerRepository,
      )
    }

    initializer {
      PlaceGroupsViewModel(
        componentRepository = cluedoApplication().container.placeRepository,
      )
    }
    initializer {
      PlaceGroupEntryViewModel(
        componentRepository = cluedoApplication().container.placeRepository,
      )
    }
    initializer {
      PlaceGroupEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.placeRepository,
      )
    }
    initializer {
      PlaceGroupViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.placeRepository,
      )
    }
    initializer {
      PlaceEntryViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.placeRepository,
      )
    }
    initializer {
      PlaceEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.placeRepository,
      )
    }

    initializer {
      WeaponGroupsViewModel(
        componentRepository = cluedoApplication().container.weaponRepository,
      )
    }
    initializer {
      WeaponGroupEntryViewModel(
        componentRepository = cluedoApplication().container.weaponRepository,
      )
    }
    initializer {
      WeaponGroupEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.weaponRepository,
      )
    }
    initializer {
      WeaponGroupViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.weaponRepository,
      )
    }
    initializer {
      WeaponEntryViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.weaponRepository,
      )
    }
    initializer {
      WeaponEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        componentRepository = cluedoApplication().container.weaponRepository,
      )
    }

    initializer {
      GamesViewModel(
        gameRepository = cluedoApplication().container.gameRepository,
      )
    }
    initializer {
      GameViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        gameRepository = cluedoApplication().container.gameRepository
      )
    }

    initializer {
      PlayerGroupSelectionViewModel(
        playerRepository = cluedoApplication().container.playerRepository,
      )
    }
    initializer {
      PlaceGroupSelectionViewModel(
        placeRepository = cluedoApplication().container.placeRepository,
      )
    }
    initializer {
      WeaponGroupSelectionViewModel(
        weaponRepository = cluedoApplication().container.weaponRepository,
      )
    }
    initializer {
      GameEntryViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        gameRepository = cluedoApplication().container.gameRepository,
      )
    }
    initializer {
      GamePlayerViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        gameRepository = cluedoApplication().container.gameRepository,
      )
    }
    initializer {
      GameEditViewModel(
        savedStateHandle = this.createSavedStateHandle(),
        gameRepository = cluedoApplication().container.gameRepository,
      )
    }
  }
}

fun CreationExtras.cluedoApplication(): CluedoApplication {
  return (this[AndroidViewModelFactory.APPLICATION_KEY] as CluedoApplication)
}
