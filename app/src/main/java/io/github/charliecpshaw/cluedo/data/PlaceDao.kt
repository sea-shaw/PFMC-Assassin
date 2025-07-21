package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT id FROM place WHERE group_id = :groupId AND is_active")
    suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Query("SELECT * FROM place WHERE id = :id")
    fun get(id: Long): Flow<Place>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(place: Place): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(place: Place): Int

    @Delete
    suspend fun delete(place: Place): Int
}
