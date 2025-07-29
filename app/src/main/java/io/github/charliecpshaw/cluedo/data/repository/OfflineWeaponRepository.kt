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
    canDeleteComponent = { weaponId, gamePlayerDao ->
        gamePlayerDao.canDeletePlace(weaponId)
    },
    onDeleteComponent = { weaponId, gamePlayerDao ->
        gamePlayerDao.replaceWeaponWithRandom(weaponId)
    }
)
