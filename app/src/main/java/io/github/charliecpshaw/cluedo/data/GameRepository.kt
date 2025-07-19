package io.github.charliecpshaw.cluedo.data

import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getAll(): Flow<List<Game>>

    fun get(id: Long): Flow<Game>

    suspend fun insert(game: Game): Long

    suspend fun update(game: Game): Int

    suspend fun delete(game: Game): Int
}
