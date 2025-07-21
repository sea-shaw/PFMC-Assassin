package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GamePlayerDao {
    @Query("SELECT * FROM game_player WHERE id = :id")
    suspend fun get(id: Long): GamePlayer

    @Query("SELECT MAX(id) FROM game_player")
    suspend fun getMaxId(): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(gamePlayers: List<GamePlayer>): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(gamePlayer: GamePlayer): Int

    @Delete
    suspend fun delete(gamePlayer: GamePlayer): Int
}
