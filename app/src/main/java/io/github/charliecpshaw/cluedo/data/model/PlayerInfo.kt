package io.github.charliecpshaw.cluedo.data.model

data class PlayerInfo(
    val id: Long,
    val playerId: Long,
    val playerName: String,
    val isAlive: Boolean,
    val targetId: Long?,
    val targetName: String?,
    val placeId: Long?,
    val placeName: Long?,
    val weaponId: Long?,
    val weaponName: String?,
)
