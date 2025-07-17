package io.github.charliecpshaw.cluedo.services

import io.github.charliecpshaw.cluedo.data.GamePlayer
import java.util.Random
import java.lang.Math.ceilDiv


fun makeGame(
    gameId: Long,
    gamePlayerIdStart: Long,
    playerIds: List<Long>,
    placeIds: List<Long>,
    weaponIds: List<Long>,
    random: Random = Random(),
): List<GamePlayer> {
    val shuffledPlayerIds = playerIds.shuffled(random)
    val shuffledPlaceIds = placeIds.extendAndShuffle(playerIds.size, random)
    val shuffledWeaponIds = weaponIds.extendAndShuffle(playerIds.size, random)
    val gamePlayers = List(
        size = playerIds.size,
        init = { index ->
            GamePlayer(
                id = gamePlayerIdStart + index,
                gameId = gameId,
                playerId = shuffledPlayerIds[index],
                isAlive = true,
                targetId = gamePlayerIdStart + ((index + 1).rem(playerIds.size)),
                deathPlaceId = shuffledPlaceIds[index],
                deathWeaponId = shuffledWeaponIds[index],
            )
        }
    )
    return gamePlayers
}

private fun <T> List<T>.extendAndShuffle(
    size: Int,
    random: Random = Random(),
) = List(ceilDiv(size, this.size)) { this }.flatMap { it.shuffled(random) }