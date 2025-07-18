package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player WHERE group_id = :groupId ORDER BY name ASC")
    fun getAllFromGroup(groupId: Long): Flow<List<Player>>

    @Query("SELECT * FROM player WHERE id = :id")
    fun get(id: Long): Flow<Player>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(player: Player): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(player: Player): Int

    @Delete
    suspend fun delete(player: Player): Int
}