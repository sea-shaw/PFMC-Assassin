package io.github.charliecpshaw.cluedo.data

import android.content.Context
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflineComponentRepository

interface AppContainer {
    val playerRepository: ComponentRepository<Player, PlayerGroup>
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
            onDeleteComponent = { id, gamePlayerDao ->
                gamePlayerDao.removePlayerFromTargets(id)
                gamePlayerDao.deletePlayerInstances(id)
            }
        )
    }
}
