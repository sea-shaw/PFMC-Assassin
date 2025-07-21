package io.github.charliecpshaw.cluedo.data

import kotlinx.coroutines.flow.Flow
import java.lang.Math.ceilDiv
import java.time.Instant
import java.util.Random

class OfflineGameRepository(
    private val gameDao: GameDao,
    private val gamePlayerDao: GamePlayerDao,
    private val playerInfoDao: PlayerInfoDao,
    private val playerDao: PlayerDao,
    private val placeDao: PlaceDao,
    private val weaponDao: WeaponDao,
) : GameRepository {

    override suspend fun createGame(
        name: String,
        startInstant: Instant,
        playerGroupId: Long,
        placeGroupId: Long,
        weaponGroupId: Long
    ) {
        val game = Game(
            name = name,
            start = startInstant,
            end = null,
            playerGroupId = playerGroupId,
            placeGroupId = placeGroupId,
            weaponGroupId = weaponGroupId,
        )

        val gameId = gameDao.insert(game)
        val gamePlayerIdStart = gamePlayerDao.getMaxId() + 1

        val playerIds = playerDao.getAllActiveIdsInGroup(playerGroupId)
        val placeIds = placeDao.getAllActiveIdsInGroup(placeGroupId)
        val weaponIds = weaponDao.getAllActiveIdsInGroup(weaponGroupId)

        val gamePlayers = createGamePlayers(
            gameId = gameId,
            gamePlayerIdStart = gamePlayerIdStart,
            playerIds = playerIds,
            placeIds = placeIds,
            weaponIds = weaponIds,
        )

        gamePlayerDao.insertAll(gamePlayers)
    }

    override fun getAllGamesStream(): Flow<List<Game>> {
        return gameDao.getAllGamesStream()
    }

    override fun getGameStream(gameId: Long): Flow<Game> {
        return gameDao.getGameStream(gameId)
    }

    override fun getAllAlivePlayersInGameStream(gameId: Long): Flow<List<PlayerInfo>> {
        return playerInfoDao.getAllAlivePlayersInGameStream(gameId)
    }

    override fun getPlayerStream(gamePlayerId: Long): Flow<PlayerInfo> {
        return playerInfoDao.getPlayerStream(gamePlayerId)
    }

    override suspend fun killTarget(
        playerId: Long,
        instant: Instant,
    ) {
        val player = gamePlayerDao.get(playerId)
        val target = gamePlayerDao.get(player.targetId)
        val playerWithNewTarget = player.copy(targetId = target.targetId)
        val killedTarget = target.copy(isAlive = false)
        gamePlayerDao.update(playerWithNewTarget)
        gamePlayerDao.update(killedTarget)

        if (playerWithNewTarget.targetId == playerWithNewTarget.id) {
            val game = gameDao.getGame(player.gameId)
            val finishedGame = game.copy(end = instant)
            gameDao.update(finishedGame)
        }
    }

    override suspend fun removePlayer(playerId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun shuffleGame(gameId: Long) {
        TODO("Not yet implemented")
    }
}

private fun createGamePlayers(
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
