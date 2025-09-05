package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.Weapon
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao : ComponentDao<Weapon> {
    @Query("SELECT * FROM weapon WHERE id = :id")
    override fun getStream(id: Long): Flow<Weapon?>

    @Query("SELECT * FROM weapon WHERE group_id = :groupId ORDER BY name ASC")
    override fun getAllInGroupStream(groupId: Long): Flow<List<Weapon>>

    @Query("SELECT id FROM weapon WHERE group_id = :groupId AND is_active")
    override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    @Query("INSERT INTO weapon (name, group_id, is_active) VALUES (:name, :groupId, :isActive)")
    override suspend fun insert(name: String, groupId: Long, isActive: Boolean): Long

    @Query("UPDATE weapon SET name = :name, is_active = :isActive WHERE id = :id")
    override suspend fun update(id: Long, name: String, isActive: Boolean): Int

    @Query(
        value = """
            SELECT CASE WHEN EXISTS (
                SELECT new_weapon.id
                FROM weapon new_weapon
                WHERE new_weapon.id <> :id
                AND new_weapon.is_active
                AND new_weapon.group_id = (
                    SELECT current_weapon.group_id
                    FROM weapon current_weapon
                    WHERE current_weapon.id = :id
                )
            ) THEN 1
            ELSE 0
            END
        """
    )
    suspend fun canDelete(id: Long): Boolean

    @Query("DELETE FROM weapon WHERE id = :id")
    override suspend fun delete(id: Long): Int

    @Query(
        value = """
            UPDATE game_player
            SET death_weapon_id = (
                SELECT new_weapon.id
                FROM weapon new_weapon
                WHERE new_weapon.group_id = (
                    SELECT current_weapon.group_id
                    FROM weapon current_weapon
                    WHERE current_weapon.id = :id
                )
                AND new_weapon.id <> :id
                ORDER BY RANDOM()
                LIMIT 1
            )
            WHERE death_weapon_id = :id
        """
    )
    suspend fun replaceWithRandomInGames(id: Long)
}
