package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

class WeaponEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : ComponentEditViewModel<Weapon, WeaponGroup>(
    savedStateHandle,
    componentRepository,
)

class WeaponEntryViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : ComponentEntryViewModel<Weapon, WeaponGroup>(
    savedStateHandle,
    componentRepository,
)

class WeaponGroupEntryViewModel(
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : GroupEntryViewModel<Weapon, WeaponGroup>(
    componentRepository,
)


class WeaponGroupEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : GroupEditViewModel<Weapon, WeaponGroup>(
    savedStateHandle,
    componentRepository,
)

class WeaponGroupsViewModel(
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : GroupsViewModel<Weapon, WeaponGroup>(
    componentRepository,
)

class WeaponGroupViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : GroupViewModel<Weapon, WeaponGroup>(
    savedStateHandle,
    componentRepository,
)
