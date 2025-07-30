package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerGroupDao
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup

class OfflinePlayerRepository(
    playerGroupDao: PlayerGroupDao,
    playerDao: PlayerDao,
    gamePlayerDao: GamePlayerDao,
) : OfflineComponentRepository<Player, PlayerGroup>(
    groupDao = playerGroupDao,
    componentDao = playerDao,
    gamePlayerDao = gamePlayerDao,
) {
    override suspend fun canDeleteComponent(id: Long): Boolean {
        return true
    }

    override suspend fun onDeleteComponent(id: Long) {
        gamePlayerDao.removePlayerFromTargets(id)
    }
}
