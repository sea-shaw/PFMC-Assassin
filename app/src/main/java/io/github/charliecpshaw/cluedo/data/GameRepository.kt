package io.github.charliecpshaw.cluedo.data

import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.time.Instant

interface GameRepository {

    suspend fun createGame(
        name: String,
        startInstant: Instant,
        playerGroupId: Long,
        placeGroupId: Long,
        weaponGroupId: Long,
    )

    fun getAllGamesStream(): Flow<List<Game>>

    fun getGameStream(gameId: Long): Flow<Game>

    fun getAllAlivePlayersInGameStream(gameId: Long): Flow<List<PlayerInfo>>

    fun getPlayerStream(gamePlayerId: Long): Flow<PlayerInfo>

    suspend fun killTarget(playerId: Long, instant: Instant)

    suspend fun removePlayer(playerId: Long)

    suspend fun shuffleGame(gameId: Long)

}
