package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.charliecpshaw.cluedo.data.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : ComponentDao<Place> {
  @Query("SELECT * FROM place WHERE id = :id")
  override fun getStream(id: Long): Flow<Place?>

  @Query("SELECT * FROM place WHERE group_id = :groupId ORDER BY name ASC")
  override fun getAllInGroupStream(groupId: Long): Flow<List<Place>>

  @Query("SELECT id FROM place WHERE group_id = :groupId AND is_active")
  override suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>

  override suspend fun insert(component: Place): Long = with(component) {
    return insert(name, groupId, isActive)
  }

  @Query("INSERT INTO place (name, group_id, is_active) VALUES (:name, :groupId, :isActive)")
  suspend fun insert(name: String, groupId: Long, isActive: Boolean): Long

  override suspend fun update(component: Place): Int = with(component) {
    return update(id, name, isActive)
  }

  @Query("UPDATE place SET name = :name, is_active = :isActive WHERE id = :id")
  suspend fun update(id: Long, name: String, isActive: Boolean): Int

  @Query(
    value = """
      SELECT CASE WHEN EXISTS (
        SELECT new_place.id
        FROM place new_place
        WHERE new_place.id <> :id
        AND new_place.is_active
        AND new_place.group_id = (
          SELECT current_place.group_id
          FROM place current_place
          WHERE current_place.id = :id
        )
      ) THEN 1
      ELSE 0
      END
    """
  )
  suspend fun canDelete(id: Long): Boolean

  @Query("DELETE FROM place WHERE id = :id")
  override suspend fun delete(id: Long): Int

  @Query(
    value = """
      UPDATE game_player
      SET death_place_id = (
        SELECT new_place.id
        FROM place new_place
        WHERE new_place.group_id = (
          SELECT current_place.group_id
          FROM place current_place
          WHERE current_place.id = :id
        )
        AND new_place.id <> :id
        AND new_place.is_active
        ORDER BY RANDOM()
        LIMIT 1
      )
      WHERE death_place_id = :id
    """
  )
  suspend fun replaceWithRandomInGames(id: Long)
}
