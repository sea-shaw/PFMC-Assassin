package io.github.charliecpshaw.cluedo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class GroupSelectionViewModel<C : Component, G : Group<C>>(
    componentRepository: ComponentRepository<C, G>,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val uiState: StateFlow<GroupsUiState<G>> = componentRepository.getNonEmptyGroupsStream()
        .filterNotNull()
        .map { GroupsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GroupsUiState(),
        )
}

class PlayerGroupSelectionViewModel(
    playerRepository: ComponentRepository<Player, PlayerGroup>,
) : GroupSelectionViewModel<Player, PlayerGroup>(
    componentRepository = playerRepository,
)

class PlaceGroupSelectionViewModel(
    placeRepository: ComponentRepository<Place, PlaceGroup>,
) : GroupSelectionViewModel<Place, PlaceGroup>(
    componentRepository = placeRepository,
)

class WeaponGroupSelectionViewModel(
    weaponRepository: ComponentRepository<Weapon, WeaponGroup>,
) : GroupSelectionViewModel<Weapon, WeaponGroup>(
    componentRepository = weaponRepository,
)
