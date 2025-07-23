package io.github.charliecpshaw.cluedo.data

import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getPlayerGroupStream(id: Long): Flow<PlayerGroup>

    fun getAllPlayerGroupsStream(): Flow<List<PlayerGroup>>

    fun getPlayerStream(id: Long): Flow<Player>

    fun getAllPlayersInGroupStream(groupId: Long): Flow<List<Player>>

    fun getAllActivePlayersInGroupStream(groupId: Long): Flow<List<Player>>

    suspend fun insertGroup(name: String): Long

    suspend fun insertPlayer(name: String, groupId: Long): Long
}
