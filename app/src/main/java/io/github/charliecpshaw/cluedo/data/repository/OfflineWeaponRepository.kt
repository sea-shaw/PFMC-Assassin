package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.WeaponDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponGroupDao
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup

class OfflineWeaponRepository(
    weaponGroupDao: WeaponGroupDao,
    private val weaponDao: WeaponDao,
) : OfflineComponentRepository<Weapon, WeaponGroup>(
    groupDao = weaponGroupDao,
    componentDao = weaponDao,
) {
    override suspend fun canDeleteComponent(id: Long): Boolean {
        return weaponDao.canDelete(id)
    }

    override suspend fun onDeleteComponent(id: Long) {
        weaponDao.replaceWithRandomInGames(id)
    }
}
