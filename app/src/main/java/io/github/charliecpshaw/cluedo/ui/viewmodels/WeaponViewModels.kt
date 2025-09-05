package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.ui.navigation.WeaponEditDestination
import io.github.charliecpshaw.cluedo.ui.navigation.WeaponEntryDestination
import io.github.charliecpshaw.cluedo.ui.navigation.WeaponGroupDestination
import io.github.charliecpshaw.cluedo.ui.navigation.WeaponGroupEditDestination

class WeaponEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : ComponentEditViewModel<Weapon, WeaponGroup>(
    id = savedStateHandle.toRoute<WeaponEditDestination>().id,
    componentRepository = componentRepository,
)

class WeaponEntryViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Weapon, WeaponGroup>,
) : ComponentEntryViewModel<Weapon, WeaponGroup>(
    groupId = savedStateHandle.toRoute<WeaponEntryDestination>().groupId,
    componentRepository = componentRepository,
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
    id = savedStateHandle.toRoute<WeaponGroupEditDestination>().id,
    componentRepository = componentRepository,
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
    groupId = savedStateHandle.toRoute<WeaponGroupDestination>().id,
    componentRepository = componentRepository,
)
