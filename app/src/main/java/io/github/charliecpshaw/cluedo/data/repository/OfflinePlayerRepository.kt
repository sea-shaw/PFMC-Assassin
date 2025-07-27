package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.dao.PlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerGroupDao
import kotlinx.coroutines.flow.Flow

class OfflinePlayerRepository(
    private val playerGroupDao: PlayerGroupDao,
    private val playerDao: PlayerDao,
    private val gamePlayerDao: GamePlayerDao,
) : PlayerRepository {
    override fun getPlayerGroupStream(id: Long): Flow<PlayerGroup?> {
        return playerGroupDao.getStream(id)
    }

    override fun getAllPlayerGroupsStream(): Flow<List<PlayerGroup>> {
        return playerGroupDao.getAllStream()
    }

    override fun getPlayerStream(id: Long): Flow<Player?> {
        return playerDao.getStream(id)
    }

    override fun getAllPlayersInGroupStream(groupId: Long): Flow<List<Player>> {
        return playerDao.getAllInGroupStream(groupId)
    }

    override suspend fun insertGroup(name: String): Long {
        return playerGroupDao.insert(name)
    }

    override suspend fun insertPlayer(name: String, groupId: Long, isActive: Boolean): Long {
        return playerDao.insert(name, groupId, isActive)
    }

    override suspend fun updateGroup(id: Long, name: String): Int {
        return playerGroupDao.update(id, name)
    }

    override suspend fun updatePlayer(id: Long, name: String, isActive: Boolean): Int {
        return playerDao.update(id, name, isActive)
    }

    override suspend fun deleteGroup(id: Long): Int {
        return playerGroupDao.delete(id)
    }

    override suspend fun deletePlayer(id: Long) {
        deleteGamePlayerInstances(id)
        playerDao.delete(id)
    }

    private suspend fun deleteGamePlayerInstances(playerId: Long) {
        gamePlayerDao.removePlayerFromTargets(playerId)
        gamePlayerDao.deletePlayerInstances(playerId)
    }
}
