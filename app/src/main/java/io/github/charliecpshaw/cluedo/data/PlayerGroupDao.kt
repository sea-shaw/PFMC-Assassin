package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerGroupDao {

    @Query("SELECT * FROM player_group ORDER BY name ASC")
    fun getAll(): Flow<List<PlayerGroup>>

    @Query("SELECT * FROM player_group WHERE id = :id")
    fun get(id: Long): Flow<PlayerGroup>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(playerGroup: PlayerGroup): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(playerGroup: PlayerGroup): Int

    @Delete
    suspend fun delete(playerGroup: PlayerGroup): Int
}
