package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.ui.navigation.PlayerEditDestination
import io.github.charliecpshaw.cluedo.ui.navigation.PlayerEntryDestination
import io.github.charliecpshaw.cluedo.ui.navigation.PlayerGroupDestination
import io.github.charliecpshaw.cluedo.ui.navigation.PlayerGroupEditDestination

class PlayerEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : ComponentEditViewModel<Player, PlayerGroup>(
    id = savedStateHandle.toRoute<PlayerEditDestination>().id,
    componentRepository = componentRepository,
)

class PlayerEntryViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Player, PlayerGroup>,
) : ComponentEntryViewModel<Player, PlayerGroup>(
    groupId = savedStateHandle.toRoute<PlayerEntryDestination>().groupId,
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
    id = savedStateHandle.toRoute<PlayerGroupEditDestination>().id,
    componentRepository = componentRepository,
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
    groupId = savedStateHandle.toRoute<PlayerGroupDestination>().id,
    componentRepository = componentRepository,
)
