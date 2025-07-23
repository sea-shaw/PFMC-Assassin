package io.github.charliecpshaw.cluedo.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.charliecpshaw.cluedo.CluedoApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            PlayerGroupsViewModel(cluedoApplication().container.playerRepository)
        }
    }
}

fun CreationExtras.cluedoApplication(): CluedoApplication =
    this[AndroidViewModelFactory.APPLICATION_KEY] as CluedoApplication