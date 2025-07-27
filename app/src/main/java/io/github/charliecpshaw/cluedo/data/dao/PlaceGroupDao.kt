package io.github.charliecpshaw.cluedo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceGroupDao : GroupDao<PlaceGroup> {

    @Query("SELECT * FROM place_group ORDER BY name ASC")
    override fun getAllStream(): Flow<List<PlaceGroup>>

    @Query("SELECT * FROM place_group WHERE id = :id")
    override fun getStream(id: Long): Flow<PlaceGroup?>

    @Query("INSERT INTO place_group (name) VALUES (:name)")
    override suspend fun insert(name: String): Long

    @Query("UPDATE place_group SET name = :name WHERE id = :id")
    override suspend fun update(id: Long, name: String): Int

    @Query("DELETE FROM place_group WHERE id = :id")
    override suspend fun delete(id: Long): Int
}
