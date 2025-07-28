package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.GamePlayer

@Dao
interface GamePlayerDao {
    @Query("SELECT * FROM game_player WHERE game_id = :gameId AND player_id = :playerId")
    suspend fun get(gameId: Long, playerId: Long): GamePlayer

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insertAll(gamePlayers: List<GamePlayer>): List<Long>

    @Update(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun update(gamePlayer: GamePlayer): Int

    @Query("DELETE FROM game_player WHERE game_id = :gameId AND player_id = :playerId")
    suspend fun delete(gameId: Long, playerId: Long): Int

    @Query(
        value = """
        UPDATE game_player
        SET target_id = (
            SELECT target.target_id
            FROM game_player target
            WHERE target.player_id = :playerId
            AND target.game_id = game_id
        )
        WHERE player_id IN (
            SELECT player.player_id
            FROM game_player player
            WHERE player.target_id = :playerId
        )
    """
    )
    suspend fun removePlayerFromTargets(playerId: Long)

    @Query(
        value = """
        DELETE FROM game_player
        WHERE game_player.player_id = :playerId
    """
    )
    suspend fun deletePlayerInstances(playerId: Long)
}
