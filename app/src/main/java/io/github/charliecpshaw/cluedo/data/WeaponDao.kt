package io.github.charliecpshaw.cluedo.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface WeaponDao {
    @Query("SELECT * FROM weapon WHERE group_id = :groupId ORDER BY name ASC")
    fun getAllFromGroup(groupId: Long): Flow<List<Weapon>>

    @Query("SELECT * FROM weapon WHERE id = :id")
    fun get(id: Long): Flow<Weapon>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(weapon: Weapon): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(weapon: Weapon): Int

    @Delete
    suspend fun delete(weapon: Weapon): Int
}