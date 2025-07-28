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
            SELECT CASE WHEN EXISTS (
                SELECT new_place.id
                FROM place new_place
                WHERE new_place.id <> :placeId
                AND new_place.is_active
                AND new_place.group_id = (
                    SELECT current_place.group_id
                    FROM place current_place
                    WHERE current_place.id = :placeId
                )
            ) THEN 1
            ELSE 0
            END
        """
    )
    suspend fun canDeletePlace(placeId: Long): Boolean

    @Query(
        value = """
            UPDATE game_player
            SET death_place_id = (
                SELECT new_place.id
                FROM place new_place
                WHERE new_place.group_id = (
                    SELECT current_place.group_id
                    FROM place current_place
                    WHERE current_place.id = :placeId
                )
                AND new_place.id <> :placeId
                AND new_place.is_active
                ORDER BY RANDOM()
                LIMIT 1
            )
            WHERE death_place_id = :placeId
        """
    )
    suspend fun replacePlaceWithRandom(placeId: Long)

    @Query(
        value = """
            SELECT CASE WHEN EXISTS (
                SELECT new_weapon.id
                FROM weapon new_weapon
                WHERE new_weapon.id <> :weaponId
                AND new_weapon.is_active
                AND new_weapon.group_id = (
                    SELECT current_weapon.group_id
                    FROM weapon current_weapon
                    WHERE current_weapon.id = :weaponId
                )
            ) THEN 1
            ELSE 0
            END
        """
    )
    suspend fun canDeleteWeapon(weaponId: Long): Boolean

    @Query(
        value = """
            UPDATE game_player
            SET death_place_id = (
                SELECT new_weapon.id
                FROM weapon new_weapon
                WHERE new_weapon.group_id = (
                    SELECT current_weapon.group_id
                    FROM weapon current_weapon
                    WHERE current_weapon.id = :weaponId
                )
                AND new_weapon.id <> :weaponId
                ORDER BY RANDOM()
                LIMIT 1
            )
            WHERE death_place_id = :weaponId
        """
    )
    suspend fun replaceWeaponWithRandom(weaponId: Long)
}
