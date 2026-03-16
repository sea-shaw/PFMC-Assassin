package io.github.charliecpshaw.cluedo.data.model

data class PlayerInfo(
  val gameId: Long,
  val playerId: Long,
  val playerName: String,
  val playerEmailAddress: String?,
  val targetId: Long,
  val targetName: String,
  val placeId: Long,
  val placeName: String,
  val weaponId: Long,
  val weaponName: String,
)
