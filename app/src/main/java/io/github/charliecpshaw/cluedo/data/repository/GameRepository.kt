package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.model.Game
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import kotlinx.coroutines.flow.Flow
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

    suspend fun shuffleGame(gameId: Long)

}
