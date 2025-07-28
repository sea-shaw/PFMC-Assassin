package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository

class PlaceEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : ComponentEditViewModel<Place, PlaceGroup>(
    savedStateHandle,
    componentRepository,
)

class PlaceEntryViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : ComponentEntryViewModel<Place, PlaceGroup>(
    savedStateHandle,
    componentRepository,
)

class PlaceGroupEntryViewModel(
    componentRepository: ComponentRepository<Place, PlaceGroup>
) : GroupEntryViewModel<Place, PlaceGroup>(
    componentRepository,
)


class PlaceGroupEditViewModel(
    savedStateHandle: SavedStateHandle,
    componentRepository: ComponentRepository<Place, PlaceGroup>,
) : GroupEditViewModel<Place, PlaceGroup>(
    savedStateHandle,
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
    savedStateHandle,
    componentRepository,
)
