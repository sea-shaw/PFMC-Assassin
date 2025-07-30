package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceGroupDao
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup

class OfflinePlaceRepository(
    placeGroupDao: PlaceGroupDao,
    placeDao: PlaceDao,
    gamePlayerDao: GamePlayerDao,
) : OfflineComponentRepository<Place, PlaceGroup>(
    groupDao = placeGroupDao,
    componentDao = placeDao,
    gamePlayerDao = gamePlayerDao,
) {
    override suspend fun canDeleteComponent(id: Long): Boolean {
        return gamePlayerDao.canDeletePlace(id)
    }

    override suspend fun onDeleteComponent(id: Long) {
        gamePlayerDao.replacePlaceWithRandom(id)
    }
}
