package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponGroupDao {

    @Query("SELECT * FROM weapon_group ORDER BY name ASC")
    fun getAll(): Flow<List<WeaponGroup>>

    @Query("SELECT * FROM weapon_group WHERE id = :id")
    fun get(id: Long): Flow<WeaponGroup>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(weaponGroup: WeaponGroup): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(weaponGroup: WeaponGroup): Int

    @Delete
    suspend fun delete(weaponGroup: WeaponGroup): Int
}
