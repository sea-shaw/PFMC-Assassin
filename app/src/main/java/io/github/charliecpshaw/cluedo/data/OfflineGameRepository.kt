package io.github.charliecpshaw.cluedo.data

import kotlinx.coroutines.flow.Flow

class OfflineGameRepository(
    private val gameDao: GameDao
) : GameRepository {
    override fun getAll(): Flow<List<Game>> {
        return gameDao.getAll()
    }

    override fun get(id: Long): Flow<Game> {
        return gameDao.get(id)
    }

    override suspend fun insert(game: Game): Long {
        return gameDao.insert(game)
    }

    override suspend fun update(game: Game): Int {
        return gameDao.update(game)
    }

    override suspend fun delete(game: Game): Int {
        return gameDao.delete(game)
    }
}