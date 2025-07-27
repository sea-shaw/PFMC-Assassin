package io.github.charliecpshaw.cluedo.data.dao

import io.github.charliecpshaw.cluedo.data.model.Component
import kotlinx.coroutines.flow.Flow

interface ComponentDao<T : Component> {
    fun getStream(id: Long): Flow<T?>
    fun getAllInGroupStream(groupId: Long): Flow<List<T>>
    suspend fun getAllActiveIdsInGroup(groupId: Long): List<Long>
    suspend fun insert(name: String, groupId: Long, isActive: Boolean): Long
    suspend fun update(id: Long, name: String, isActive: Boolean): Int
    suspend fun delete(id: Long): Int
}
