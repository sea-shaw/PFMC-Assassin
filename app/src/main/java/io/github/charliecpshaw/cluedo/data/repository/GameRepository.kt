package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.model.Game
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface GameRepository {

    suspend fun createGame(
        name: String,
        playerGroupId: Long,
        placeGroupId: Long,
        weaponGroupId: Long,
    ): Long

    fun getAllGamesStream(): Flow<List<Game>>

    fun getGameStream(gameId: Long): Flow<Game?>

    fun getAllAlivePlayersInGameStream(gameId: Long): Flow<List<PlayerInfo>>

    fun getPlayerStream(gameId: Long, playerId: Long): Flow<PlayerInfo?>

    suspend fun killTarget(gameId: Long, playerId: Long)

    suspend fun shuffleGame(gameId: Long)

    suspend fun deleteGame(gameId: Long)
}
