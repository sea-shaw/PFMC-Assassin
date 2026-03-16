package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.GamePlayer

@Dao
interface GamePlayerDao {
  @Query("SELECT * FROM game_player WHERE game_id = :gameId AND player_id = :playerId")
  suspend fun get(gameId: Long, playerId: Long): GamePlayer

  @Query("SELECT player_id FROM game_player WHERE game_id = :gameId")
  suspend fun getAllPlayerIdsInGame(gameId: Long): List<Long>

  @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
  suspend fun insertAll(gamePlayers: List<GamePlayer>): List<Long>

  @Update(onConflict = OnConflictStrategy.Companion.ABORT)
  suspend fun update(gamePlayer: GamePlayer): Int

  @Query("DELETE FROM game_player WHERE game_id = :gameId AND player_id = :playerId")
  suspend fun delete(gameId: Long, playerId: Long): Int

  @Query("DELETE FROM game_player WHERE game_id = :gameId")
  suspend fun deleteAllPlayersInGame(gameId: Long)

  @Transaction
  suspend fun killPlayer(gameId: Long, playerId: Long) {
    val player = get(gameId, playerId)
    val killer = getKiller(gameId, playerId)
    updateTarget(gameId, killer.playerId, player.targetId)
    delete(gameId, playerId)
  }

  @Query("SELECT * FROM game_player WHERE game_id = :gameId and target_id = :playerId")
  suspend fun getKiller(gameId: Long, playerId: Long): GamePlayer

  @Query("UPDATE game_player SET target_id = :newTargetId WHERE game_id = :gameId AND player_id = :playerId")
  suspend fun updateTarget(gameId: Long, playerId: Long, newTargetId: Long)
}
