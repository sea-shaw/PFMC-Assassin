package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : ComponentDao<Place> {
    @Query("SELECT * FROM place WHERE id = :id")
    override fun getStream(id: Long): Flow<Place?>

    @Query("SELECT * FROM place WHERE group_id = :groupId ORDER BY name ASC")
    override fun getAllInGroupStream(groupId: Long): Flow<List<Place>>

    @Query("SELECT id FROM place WHERE group_id = :groupId AND is_active")
    override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Query("INSERT INTO place (name, group_id, is_active) VALUES (:name, :groupId, :isActive)")
    override suspend fun insert(name: String, groupId: Long, isActive: Boolean): Long

    @Query("UPDATE place SET name = :name, is_active = :isActive WHERE id = :id")
    override suspend fun update(id: Long, name: String, isActive: Boolean): Int

    @Query("DELETE FROM place WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
