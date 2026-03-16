package io.github.charliecpshaw.cluedo.data.dao

import io.github.charliecpshaw.cluedo.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupDao<G : Group<*>> {
  fun getStream(id: Long): Flow<G?>
  fun getAllStream(): Flow<List<G>>
  fun getAllNonEmptyStream(): Flow<List<G>>
  suspend fun insert(name: String): Long
  suspend fun update(id: Long, name: String): Int
  suspend fun delete(id: Long): Int
}
