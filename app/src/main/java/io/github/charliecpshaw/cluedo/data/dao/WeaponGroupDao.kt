package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponGroupDao : GroupDao<WeaponGroup> {

    @Query("SELECT * FROM weapon_group ORDER BY name ASC")
    override fun getAllStream(): Flow<List<WeaponGroup>>

    @Query("SELECT * FROM weapon_group WHERE id = :id")
    override fun getStream(id: Long): Flow<WeaponGroup?>

    @Query("INSERT INTO weapon_group (name) VALUES (:name)")
    override suspend fun insert(name: String): Long

    @Query("UPDATE weapon_group SET name = :name WHERE id = :id")
    override suspend fun update(id: Long, name: String): Int

    @Query("DELETE FROM weapon_group WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
