package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getPlayerGroupStream(id: Long): Flow<PlayerGroup?>

    fun getAllPlayerGroupsStream(): Flow<List<PlayerGroup>>

    fun getPlayerStream(id: Long): Flow<Player?>

    fun getAllPlayersInGroupStream(groupId: Long): Flow<List<Player>>

    suspend fun insertGroup(name: String): Long

    suspend fun insertPlayer(name: String, groupId: Long, isActive: Boolean): Long

    suspend fun updateGroup(id: Long, name: String): Int

    suspend fun updatePlayer(id: Long, name: String, isActive: Boolean): Int

    suspend fun deleteGroup(id: Long): Int

    suspend fun deletePlayer(id: Long)
}
