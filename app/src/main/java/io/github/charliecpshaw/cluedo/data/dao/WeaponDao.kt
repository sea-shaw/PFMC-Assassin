package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao : ComponentDao<Weapon> {
    @Query("SELECT * FROM weapon WHERE id = :id")
    override fun getStream(id: Long): Flow<Weapon?>

    @Query("SELECT * FROM weapon WHERE group_id = :groupId")
    override fun getAllInGroupStream(groupId: Long): Flow<List<Weapon>>

    @Query("SELECT id FROM weapon WHERE group_id = :groupId AND is_active")
    override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    override suspend fun insert(entry: Weapon): Long

    @Query("UPDATE weapon SET name = :name, is_active = :isActive WHERE id = :id")
    override suspend fun update(id: Long, name: String, isActive: Boolean): Int

    @Query("DELETE FROM weapon WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
