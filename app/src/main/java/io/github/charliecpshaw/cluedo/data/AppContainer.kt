package io.github.charliecpshaw.cluedo.data

import android.content.Context

interface AppContainer {
    val playerRepository: PlayerRepository
}

class AppDataContainer(
    private val context: Context,
) : AppContainer {
    override val playerRepository: PlayerRepository by lazy {
        val database = CluedoDatabase.getDatabase(context)
        OfflinePlayerRepository(
            playerGroupDao = database.playerGroupDao(),
            playerDao = database.playerDao(),
        )
    }
}
