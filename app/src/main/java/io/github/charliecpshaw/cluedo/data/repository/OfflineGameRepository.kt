package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.GameDao
import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerInfoDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponDao
import io.github.charliecpshaw.cluedo.data.model.Game
import io.github.charliecpshaw.cluedo.data.model.GamePlayer
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import kotlinx.coroutines.flow.Flow
import java.lang.Math.ceilDiv
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
        playerGroupId: Long,
        placeGroupId: Long,
        weaponGroupId: Long,
    ): Long {
        val game = Game(
            name = name,
            playerGroupId = playerGroupId,
            placeGroupId = placeGroupId,
            weaponGroupId = weaponGroupId,
        )

        val gameId = gameDao.insert(game)

        val playerIds = playerDao.getAllActiveIdsInGroup(playerGroupId)
        val placeIds = placeDao.getAllActiveIdsInGroup(placeGroupId)
        val weaponIds = weaponDao.getAllActiveIdsInGroup(weaponGroupId)

        val gamePlayers = createGamePlayers(
            gameId = gameId,
            playerIds = playerIds,
            placeIds = placeIds,
            weaponIds = weaponIds,
        )

        gamePlayerDao.insertAll(gamePlayers)

        return gameId
    }

    override fun getAllGamesStream(): Flow<List<Game>> {
        return gameDao.getAllGamesStream()
    }

    override fun getGameStream(gameId: Long): Flow<Game?> {
        return gameDao.getGameStream(gameId)
    }

    override fun getAllAlivePlayersInGameStream(gameId: Long): Flow<List<PlayerInfo>> {
        return playerInfoDao.getAllPlayersInGameStream(gameId)
    }

    override fun getPlayerStream(gameId: Long, playerId: Long): Flow<PlayerInfo?> {
        return playerInfoDao.getPlayerStream(gameId, playerId)
    }

    override suspend fun killPlayer(
        gameId: Long,
        playerId: Long,
    ) {
        gamePlayerDao.killPlayer(gameId, playerId)
    }

    override suspend fun killTarget(
        gameId: Long,
        playerId: Long,
    ) {
        val player = gamePlayerDao.get(gameId, playerId)
        val target = gamePlayerDao.get(gameId, player.targetId)
        val playerWithNewTarget = player.copy(targetId = target.targetId)
        gamePlayerDao.update(playerWithNewTarget)
        gamePlayerDao.delete(gameId, player.targetId)
    }

    override suspend fun shuffleGame(gameId: Long) {
        val game = gameDao.getGame(gameId)!!
        val playerIds = gamePlayerDao.getAllPlayerIdsInGame(gameId)
        val placeIds = placeDao.getAllActiveIdsInGroup(game.placeGroupId)
        val weaponIds = weaponDao.getAllActiveIdsInGroup(game.weaponGroupId)
        val shuffledGamePlayers = createGamePlayers(
            gameId = game.id,
            playerIds = playerIds,
            placeIds = placeIds,
            weaponIds = weaponIds,
        )
        gamePlayerDao.deleteAllPlayersInGame(gameId)
        gamePlayerDao.insertAll(shuffledGamePlayers)
    }

    override suspend fun updateGameName(id: Long, name: String) {
        gameDao.updateGameName(id, name)
    }

    override suspend fun deleteGame(gameId: Long) {
        gameDao.delete(gameId)
    }
}

private fun createGamePlayers(
    gameId: Long,
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
                gameId = gameId,
                playerId = shuffledPlayerIds[index],
                targetId = shuffledPlayerIds[(index + 1).mod(shuffledPlayerIds.size)],
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
