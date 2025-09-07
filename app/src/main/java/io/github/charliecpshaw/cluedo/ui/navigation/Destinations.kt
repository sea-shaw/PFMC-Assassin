package io.github.charliecpshaw.cluedo.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object PlayersTabDestination
@Serializable object PlayerGroupsDestination
@Serializable data class PlayerGroupDestination(val id: Long)
@Serializable object PlayerGroupEntryDestination
@Serializable data class PlayerGroupEditDestination(val id: Long)
@Serializable data class PlayerEntryDestination(val groupId: Long)
@Serializable data class PlayerEditDestination(val id: Long)

@Serializable object PlacesTabDestination
@Serializable object PlaceGroupsDestination
@Serializable data class PlaceGroupDestination(val id: Long)
@Serializable object PlaceGroupEntryDestination
@Serializable data class PlaceGroupEditDestination(val id: Long)
@Serializable data class PlaceEntryDestination(val groupId: Long)
@Serializable data class PlaceEditDestination(val id: Long)

@Serializable object WeaponsTabDestination
@Serializable object WeaponGroupsDestination
@Serializable data class WeaponGroupDestination(val id: Long)
@Serializable object WeaponGroupEntryDestination
@Serializable data class WeaponGroupEditDestination(val id: Long)
@Serializable data class WeaponEntryDestination(val groupId: Long)
@Serializable data class WeaponEditDestination(val id: Long)

@Serializable object GamesTabDestination
@Serializable object GamesDestination
@Serializable object GamePlayerGroupSelectionDestination
@Serializable data class GamePlaceGroupSelectionDestination(val playerGroupId: Long)
@Serializable data class GameWeaponGroupSelectionDestination(
    val playerGroupId: Long,
    val placeGroupId: Long,
)
@Serializable data class GameEntryDestination(
    val playerGroupId: Long,
    val placeGroupId: Long,
    val weaponGroupId: Long,
)
@Serializable data class GameDestination(val id: Long)
@Serializable data class GameEditDestination(val id: Long)
@Serializable data class GamePlayerDestination(val gameId: Long, val playerId: Long)
