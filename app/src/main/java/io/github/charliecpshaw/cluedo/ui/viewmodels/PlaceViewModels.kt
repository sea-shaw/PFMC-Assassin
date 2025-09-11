package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.ui.navigation.PlaceEditDestination
import io.github.charliecpshaw.cluedo.ui.navigation.PlaceEntryDestination
import io.github.charliecpshaw.cluedo.ui.navigation.PlaceGroupDestination
import io.github.charliecpshaw.cluedo.ui.navigation.PlaceGroupEditDestination

class PlaceEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : ComponentEditViewModel<Place, PlaceGroup, PlaceDetails>(
    id = savedStateHandle.toRoute<PlaceEditDestination>().id,
    componentRepository = componentRepository,
    detailsFactory = PlaceDetailsFactory,
)

class PlaceEntryViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : ComponentEntryViewModel<Place, PlaceGroup, PlaceDetails>(
    groupId = savedStateHandle.toRoute<PlaceEntryDestination>().groupId,
    componentRepository = componentRepository,
    detailsFactory = PlaceDetailsFactory,
)

class PlaceGroupEntryViewModel(
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : GroupEntryViewModel<Place, PlaceGroup>(
    componentRepository,
)

class PlaceGroupEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : GroupEditViewModel<Place, PlaceGroup>(
    id = savedStateHandle.toRoute<PlaceGroupEditDestination>().id,
    componentRepository,
)

class PlaceGroupsViewModel(
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : GroupsViewModel<Place, PlaceGroup>(
    componentRepository,
)

class PlaceGroupViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : GroupViewModel<Place, PlaceGroup>(
    groupId = savedStateHandle.toRoute<PlaceGroupDestination>().id,
    componentRepository = componentRepository,
)
