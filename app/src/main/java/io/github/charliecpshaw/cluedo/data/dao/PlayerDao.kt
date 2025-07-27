package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao : ComponentDao<Player> {
    @Query("SELECT * FROM player WHERE id = :id")
    override fun getStream(id: Long): Flow<Player>

    @Query("SELECT * FROM player WHERE group_id = :groupId ORDER BY name ASC")
    override fun getAllInGroupStream(groupId: Long): Flow<List<Player>>

    @Query("SELECT id FROM player WHERE group_id = :groupId AND is_active")
    override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    override suspend fun insert(entry: Player): Long

    @Query(value = "UPDATE player SET name = :name, is_active = :isActive WHERE id = :id")
    override suspend fun update(id: Long, name: String, isActive: Boolean): Int

    @Query("DELETE FROM player WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
