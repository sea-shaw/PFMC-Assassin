package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import kotlinx.coroutines.flow.Flow

interface ComponentRepository<C : Component, G : Group<C>> {
  fun getGroupStream(id: Long): Flow<G?>
  fun getAllGroupsStream(): Flow<List<G>>
  fun getNonEmptyGroupsStream(): Flow<List<G>>
  fun getComponentStream(id: Long): Flow<C?>
  fun getAllComponentsInGroupStream(groupId: Long): Flow<List<C>>
  suspend fun insertGroup(name: String): Long
  suspend fun insertComponent(component: C): Long
  suspend fun updateGroup(id: Long, name: String): Int
  suspend fun updateComponent(component: C): Int
  suspend fun deleteGroup(id: Long): Int
  suspend fun deleteComponent(id: Long): Int
}
