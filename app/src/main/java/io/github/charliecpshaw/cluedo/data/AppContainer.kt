package io.github.charliecpshaw.cluedo.data

import android.content.Context
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflineComponentRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflineGameRepository

interface AppContainer {
    val playerRepository: ComponentRepository<Player, PlayerGroup>
    val placeRepository: ComponentRepository<Place, PlaceGroup>
    val weaponRepository: ComponentRepository<Weapon, WeaponGroup>

    val gameRepository: GameRepository
}

class AppDataContainer(
    private val context: Context,
) : AppContainer {
    override val playerRepository: ComponentRepository<Player, PlayerGroup> by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflineComponentRepository(
            groupDao = database.playerGroupDao(),
            componentDao = database.playerDao(),
            gamePlayerDao = database.gamePlayerDao(),
            canDeleteComponent = { _, _ -> true },
            onDeleteComponent = { id, gamePlayerDao ->
                gamePlayerDao.removePlayerFromTargets(id)
            }
        )
    }

    override val placeRepository: ComponentRepository<Place, PlaceGroup> by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflineComponentRepository(
            groupDao = database.placeGroupDao(),
            componentDao = database.placeDao(),
            gamePlayerDao = database.gamePlayerDao(),
            canDeleteComponent = { id, gamePlayerDao ->
                gamePlayerDao.canDeletePlace(id)
            },
            onDeleteComponent = { id, gamePlayerDao ->
                gamePlayerDao.replacePlaceWithRandom(id)
            }
        )
    }

    override val weaponRepository: ComponentRepository<Weapon, WeaponGroup> by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflineComponentRepository(
            groupDao = database.weaponGroupDao(),
            componentDao = database.weaponDao(),
            gamePlayerDao = database.gamePlayerDao(),
            canDeleteComponent = { id, gamePlayerDao ->
                gamePlayerDao.canDeleteWeapon(id)
            },
            onDeleteComponent = { id, gamePlayerDao ->
                gamePlayerDao.replaceWeaponWithRandom(id)
            }
        )
    }

    override val gameRepository: GameRepository by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflineGameRepository(
            gameDao = database.gameDao(),
            gamePlayerDao = database.gamePlayerDao(),
            playerInfoDao = database.playerInfoDao(),
            playerDao = database.playerDao(),
            placeDao = database.placeDao(),
            weaponDao = database.weaponDao(),
        )
    }
}
