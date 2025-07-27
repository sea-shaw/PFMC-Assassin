package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : ComponentDao<Place> {
    @Query("SELECT id FROM place WHERE group_id = :groupId AND is_active")
    override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Query("SELECT * FROM place WHERE id = :id")
    override fun getStream(id: Long): Flow<Place?>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    override suspend fun insert(entry: Place): Long

    @Query("UPDATE place SET name = :name, is_active = :isActive WHERE id = :id")
    override suspend fun update(id: Long, name: String, isActive: Boolean): Int

    @Query("DELETE FROM place WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
