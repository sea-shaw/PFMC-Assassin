package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponGroupDao
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup

class OfflineWeaponRepository(
    weaponGroupDao: WeaponGroupDao,
    weaponDao: WeaponDao,
    gamePlayerDao: GamePlayerDao,
) : OfflineComponentRepository<Weapon, WeaponGroup>(
    groupDao = weaponGroupDao,
    componentDao = weaponDao,
    gamePlayerDao = gamePlayerDao,
) {
    override suspend fun canDeleteComponent(id: Long): Boolean {
        return gamePlayerDao.canDeleteWeapon(id)
    }

    override suspend fun onDeleteComponent(id: Long) {
        gamePlayerDao.replaceWeaponWithRandom(id)
    }
}
