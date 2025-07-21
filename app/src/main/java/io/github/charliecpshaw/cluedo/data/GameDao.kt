package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game ORDER BY start_date DESC")
    fun getAllGamesStream(): Flow<List<Game>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGameStream(id: Long): Flow<Game>

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun getGame(id: Long): Game

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(game: Game): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(game: Game): Int

    @Delete
    suspend fun delete(game: Game): Int
}