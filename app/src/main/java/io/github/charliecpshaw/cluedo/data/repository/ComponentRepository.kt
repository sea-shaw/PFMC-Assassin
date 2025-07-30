package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import kotlinx.coroutines.flow.Flow

interface ComponentRepository<C : Component, G : Group> {
    fun getGroupStream(id: Long): Flow<G?>
    fun getAllGroupsStream(): Flow<List<G>>
    fun getNonEmptyGroupsStream(): Flow<List<G>>
    fun getComponentStream(id: Long): Flow<C?>
    fun getAllComponentsInGroupStream(groupId: Long): Flow<List<C>>
    suspend fun insertGroup(name: String): Long
    suspend fun insertComponent(name: String, groupId: Long, isActive: Boolean): Long
    suspend fun updateGroup(id: Long, name: String): Int
    suspend fun updateComponent(id: Long, name: String, isActive: Boolean): Int
    suspend fun deleteGroup(id: Long): Int
    suspend fun canDeleteComponent(id: Long): Boolean
    suspend fun deleteComponent(id: Long): Int
}
