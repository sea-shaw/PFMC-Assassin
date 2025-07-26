package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.GamePlayer

@Dao
interface GamePlayerDao {
    @Query("SELECT * FROM game_player WHERE id = :id")
    suspend fun get(id: Long): GamePlayer

    @Query("SELECT MAX(id) FROM game_player")
    suspend fun getMaxId(): Long

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insertAll(gamePlayers: List<GamePlayer>): List<Long>

    @Update(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun update(gamePlayer: GamePlayer): Int

    @Delete
    suspend fun delete(gamePlayer: GamePlayer): Int

    @Query(value = """
        UPDATE game_player
        SET target_id = (
            SELECT target.target_id
            FROM game_player target
            WHERE target.player_id = :playerId
            AND target.game_id = game_player.game_id
        )
        WHERE game_player.id IN (
            SELECT player.id
            FROM game_player player
            JOIN game_player target ON player.target_id = target.id
            WHERE target.player_id = :playerId
        )
    """)
    suspend fun removePlayerFromTargets(playerId: Long)

    @Query(value = """
        DELETE FROM game_player
        WHERE game_player.player_id = :playerId
    """)
    suspend fun deletePlayerInstances(playerId: Long)
}
