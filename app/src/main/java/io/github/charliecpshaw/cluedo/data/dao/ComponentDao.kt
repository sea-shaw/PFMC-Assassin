package io.github.charliecpshaw.cluedo.data.dao

import io.github.charliecpshaw.cluedo.data.model.Component
import kotlinx.coroutines.flow.Flow

interface ComponentDao<C : Component> {
  fun getStream(id: Long): Flow<C?>
  fun getAllInGroupStream(groupId: Long): Flow<List<C>>
  suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>
  suspend fun insert(component: C): Long
  suspend fun update(component: C): Int
  suspend fun delete(id: Long): Int
}
