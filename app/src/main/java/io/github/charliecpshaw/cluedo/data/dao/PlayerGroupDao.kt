package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerGroupDao {

    @Query("SELECT * FROM player_group ORDER BY name ASC")
    fun getAllStream(): Flow<List<PlayerGroup>>

    @Query("SELECT * FROM player_group WHERE id = :id")
    fun getStream(id: Long): Flow<PlayerGroup?>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insert(playerGroup: PlayerGroup): Long

    @Update(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun update(playerGroup: PlayerGroup): Int

    @Query("DELETE FROM player_group WHERE id = :id")
    suspend fun delete(id: Long): Int
}