package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.Weapon
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao {
    @Query("SELECT id FROM weapon WHERE group_id = :groupId AND is_active")
    suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Query("SELECT * FROM weapon WHERE id = :id")
    fun get(id: Long): Flow<Weapon>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insert(weapon: Weapon): Long

    @Update(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun update(weapon: Weapon): Int

    @Delete
    suspend fun delete(weapon: Weapon): Int
}