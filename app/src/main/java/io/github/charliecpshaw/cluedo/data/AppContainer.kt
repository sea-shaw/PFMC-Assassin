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
import io.github.charliecpshaw.cluedo.data.repository.OfflinePlaceRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflinePlayerRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflineWeaponRepository

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
        OfflinePlayerRepository(
            playerGroupDao = database.playerGroupDao(),
            playerDao = database.playerDao(),
            gamePlayerDao = database.gamePlayerDao(),
        )
    }

    override val placeRepository: ComponentRepository<Place, PlaceGroup> by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflinePlaceRepository(
            placeGroupDao = database.placeGroupDao(),
            placeDao = database.placeDao(),
            gamePlayerDao = database.gamePlayerDao(),
        )
    }

    override val weaponRepository: ComponentRepository<Weapon, WeaponGroup> by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflineWeaponRepository(
            weaponGroupDao = database.weaponGroupDao(),
            weaponDao = database.weaponDao(),
            gamePlayerDao = database.gamePlayerDao(),
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
