package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

class PlayerEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : ComponentEditViewModel<Player, PlayerGroup>(
    savedStateHandle,
    componentRepository,
)

class PlayerEntryViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : ComponentEntryViewModel<Player, PlayerGroup>(
    savedStateHandle,
    componentRepository,
)

class PlayerGroupEntryViewModel(
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : GroupEntryViewModel<Player, PlayerGroup>(
    componentRepository,
)

class PlayerGroupEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : GroupEditViewModel<Player, PlayerGroup>(
    savedStateHandle,
    componentRepository,
)

class PlayerGroupsViewModel(
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : GroupsViewModel<Player, PlayerGroup>(
    componentRepository,
)

class PlayerGroupViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : GroupViewModel<Player, PlayerGroup>(
    savedStateHandle,
    componentRepository,
)
