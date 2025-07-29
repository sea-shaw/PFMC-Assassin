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
    canDeleteComponent = { placeId, gamePlayerDao ->
        gamePlayerDao.canDeletePlace(placeId)
    },
    onDeleteComponent = { placeId, gamePlayerDao ->
        gamePlayerDao.replacePlaceWithRandom(placeId)
    }
)
