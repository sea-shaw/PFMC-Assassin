package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getPlayerGroupStream(id: Long): Flow<PlayerGroup?>

    fun getAllPlayerGroupsStream(): Flow<List<PlayerGroup>>

    fun getPlayerStream(id: Long): Flow<Player?>

    fun getAllPlayersInGroupStream(groupId: Long): Flow<List<Player>>

    fun getAllActivePlayersInGroupStream(groupId: Long): Flow<List<Player>>

    suspend fun insertGroup(name: String): Long

    suspend fun insertPlayer(player: Player): Long

    suspend fun deleteGroup(id: Long)
}
