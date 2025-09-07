package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerGroupDao : GroupDao<PlayerGroup> {

    @Query("SELECT * FROM player_group ORDER BY name ASC")
    override fun getAllStream(): Flow<List<PlayerGroup>>

    @Query("SELECT * FROM player_group WHERE id = :id")
    override fun getStream(id: Long): Flow<PlayerGroup?>

    @Query(
        value = """
            SELECT * FROM player_group
            WHERE EXISTS (
                SELECT player.id
                FROM player
                WHERE player.group_id = player_group.id
                AND player.is_active
            )
        """
    )
    override fun getAllNonEmptyStream(): Flow<List<PlayerGroup>>

    @Query("INSERT INTO player_group (name) VALUES (:name)")
    override suspend fun insert(name: String): Long

    @Query("UPDATE player_group SET name = :name WHERE id = :id")
    override suspend fun update(id: Long, name: String): Int

    @Query("DELETE FROM player_group WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
