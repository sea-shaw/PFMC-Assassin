package io.github.charliecpshaw.cluedo.data.dao

import io.github.charliecpshaw.cluedo.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupDao<T : Group> {
    fun getStream(id: Long): Flow<T?>
    fun getAllStream(): Flow<List<T>>
    suspend fun insert(name: String): Long
    suspend fun update(id: Long, name: String): Int
    suspend fun delete(id: Long): Int
}
