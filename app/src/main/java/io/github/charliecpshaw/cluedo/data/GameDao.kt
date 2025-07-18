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
    fun getAll(): Flow<List<Game>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun get(id: Long): Flow<Game>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(game: Game): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(game: Game): Int

    @Delete
    suspend fun delete(game: Game): Int
}