package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

private const val QUERY = """
SELECT
game_player.id AS id,
game_player.player_id as playerId,
player.name as playerName,
target_game_player.id as targetId,
target_player.name as targetName,
place.id as placeId,
place.name as placeName,
weapon.id as weaponId,
weapon.name as weaponName
FROM game_player game_player
JOIN player player ON player.id = game_player.player_id
LEFT JOIN game_player target_game_player ON game_player.target_id = target_game_player.player_id
LEFT JOIN player target_player ON target_player.id = target_game_player.player_id
LEFT JOIN place place ON place.id = target_game_player.death_place_id
LEFT JOIN weapon weapon ON weapon.id = target_game_player.death_weapon_id
"""

@Dao
interface PlayerInfoDao {
    @Query(QUERY + "WHERE game_player.id = :id")
    fun getGamePlayer(id: Int): Flow<PlayerInfo>

    @Query(QUERY + "WHERE game_player.game_id = :gameId")
    fun getAllGamePlayersInGame(gameId: Int): Flow<List<PlayerInfo>>
}
