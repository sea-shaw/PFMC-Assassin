package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import kotlinx.coroutines.flow.Flow

private const val QUERY = """
    SELECT
        game_player.game_id AS gameId,
        game_player.is_alive AS isAlive,
        player.id AS playerId,
        player.name AS playerName,
        target_player.id AS targetId,
        target_player.name AS targetName,
        place.id AS placeId,
        place.name AS placeName,
        weapon.id AS weaponId,
        weapon.name AS weaponName
        FROM game_player game_player
        JOIN game_player target_game_player
        ON game_player.target_id = target_game_player.player_id
        AND game_player.game_id = target_game_player.game_id
        JOIN player player ON game_player.player_id = player.id
        JOIN player target_player ON game_player.target_id = target_player.id
        JOIN place place ON target_game_player.death_place_id = place.id
        JOIN weapon weapon ON target_game_player.death_weapon_id = weapon.id
        WHERE game_player.game_id = :gameId
        
"""

@Dao
interface PlayerInfoDao {
    @Query(QUERY + "AND game_player.player_id = :playerId")
    fun getPlayerStream(gameId: Long, playerId: Long): Flow<PlayerInfo?>

    @Query(QUERY)
    fun getAllAlivePlayersInGameStream(gameId: Long): Flow<List<PlayerInfo>>
}
