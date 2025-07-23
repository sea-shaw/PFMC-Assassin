package io.github.charliecpshaw.cluedo.data

import kotlinx.coroutines.flow.Flow

class OfflinePlayerRepository(
    private val playerGroupDao: PlayerGroupDao,
    private val playerDao: PlayerDao,
) : PlayerRepository {
    override fun getPlayerGroupStream(id: Long): Flow<PlayerGroup> {
        TODO("Not yet implemented")
    }

    override fun getAllPlayerGroupsStream(): Flow<List<PlayerGroup>> {
        TODO("Not yet implemented")
    }

    override fun getPlayerStream(id: Long): Flow<Player> {
        TODO("Not yet implemented")
    }

    override fun getAllPlayersInGroupStream(groupId: Long): Flow<List<Player>> {
        TODO("Not yet implemented")
    }

    override fun getAllActivePlayersInGroupStream(groupId: Long): Flow<List<Player>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertGroup(name: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertPlayer(name: String, groupId: Long): Long {
        TODO("Not yet implemented")
    }

}