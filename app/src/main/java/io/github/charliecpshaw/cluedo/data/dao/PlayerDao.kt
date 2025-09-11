package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao : ComponentDao<Player> {
    @Query("SELECT * FROM player WHERE id = :id")
    override fun getStream(id: Long): Flow<Player>

    @Query("SELECT * FROM player WHERE group_id = :groupId ORDER BY name ASC")
    override fun getAllInGroupStream(groupId: Long): Flow<List<Player>>

    @Query("SELECT id FROM player WHERE group_id = :groupId AND is_active")
    override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

    override suspend fun insert(component: Player): Long = with(component) {
        return insert(name, emailAddress, groupId, isActive)
    }

    @Query("INSERT INTO player (name, email_address, group_id, is_active) VALUES (:name, :emailAddress, :groupId, :isActive)")
    suspend fun insert(name: String, emailAddress: String?, groupId: Long, isActive: Boolean): Long

    override suspend fun update(component: Player): Int = with(component) {
        return update(id, name, emailAddress, isActive)
    }

    @Query(value = "UPDATE player SET name = :name, email_address = :emailAddress, is_active = :isActive WHERE id = :id")
    suspend fun update(id: Long, name: String, emailAddress: String?, isActive: Boolean): Int

    @Query("DELETE FROM player WHERE id = :id")
    override suspend fun delete(id: Long): Int

    @Query(
        value = """
        UPDATE game_player
        SET target_id = (
            SELECT target.target_id
            FROM game_player target
            WHERE target.player_id = :id
            AND target.game_id = game_id
        )
        WHERE player_id IN (
            SELECT player.player_id
            FROM game_player player
            WHERE player.target_id = :id
        )
    """
    )
    suspend fun removeFromGames(id: Long)
}
