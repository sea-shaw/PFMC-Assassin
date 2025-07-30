package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game ORDER BY name")
    fun getAllGamesStream(): Flow<List<Game>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGameStream(id: Long): Flow<Game?>

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun getGame(id: Long): Game?

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insert(game: Game): Long

    @Update(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun update(game: Game): Int

    @Query("DELETE FROM game WHERE id = :id")
    suspend fun delete(id: Long): Int
}
