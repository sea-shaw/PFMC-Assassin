package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.PlaceDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceGroupDao
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup

class OfflinePlaceRepository(
  placeGroupDao: PlaceGroupDao,
  private val placeDao: PlaceDao,
) : OfflineComponentRepository<Place, PlaceGroup>(
  groupDao = placeGroupDao,
  componentDao = placeDao,
) {
  override suspend fun canDeleteComponent(id: Long): Boolean {
    return placeDao.canDelete(id)
  }

  override suspend fun onDeleteComponent(id: Long) {
    placeDao.replaceWithRandomInGames(id)
  }
}
