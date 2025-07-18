package io.github.charliecpshaw.cluedo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceGroupDao {

    @Query("SELECT * FROM place_group ORDER BY name ASC")
    fun getAll(): Flow<List<PlaceGroup>>

    @Query("SELECT * FROM place_group WHERE id = :id")
    fun get(id: Long): Flow<PlaceGroup>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(placeGroup: PlaceGroup): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(placeGroup: PlaceGroup): Int

    @Delete
    suspend fun delete(placeGroup: PlaceGroup): Int
}