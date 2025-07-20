package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao {
    @Query("SELECT * FROM weapon WHERE group_id = :groupId AND is_active ORDER BY name ASC")
    suspend fun getAllActiveInGroup(groupId: Long): List<Weapon>

    @Query("SELECT * FROM weapon WHERE id = :id")
    fun get(id: Long): Flow<Weapon>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(weapon: Weapon): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(weapon: Weapon): Int

    @Delete
    suspend fun delete(weapon: Weapon): Int
}