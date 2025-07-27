package io.github.charliecpshaw.cluedo.data.repository

import io.github.charliecpshaw.cluedo.data.dao.ComponentDao
import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.dao.GroupDao
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import kotlinx.coroutines.flow.Flow

class OfflineComponentRepository<C : Component, G : Group>(
    private val groupDao: GroupDao<G>,
    private val componentDao: ComponentDao<C>,
    private val gamePlayerDao: GamePlayerDao,
    private val onDeleteComponent: suspend (Long, GamePlayerDao) -> Unit,
) : ComponentRepository<C, G> {
    override fun getGroupStream(id: Long): Flow<G?> {
        return groupDao.getStream(id)
    }

    override fun getAllGroupsStream(): Flow<List<G>> {
        return groupDao.getAllStream()
    }

    override fun getComponentStream(id: Long): Flow<C?> {
        return componentDao.getStream(id)
    }

    override fun getAllComponentsInGroupStream(groupId: Long): Flow<List<C>> {
        return componentDao.getAllInGroupStream(groupId)
    }

    override suspend fun insertGroup(name: String): Long {
        return groupDao.insert(name)
    }

    override suspend fun insertComponent(
        name: String,
        groupId: Long,
        isActive: Boolean,
    ): Long {
        return componentDao.insert(name, groupId, isActive)
    }


    override suspend fun updateGroup(id: Long, name: String): Int {
        return groupDao.update(id, name)
    }

    override suspend fun updateComponent(
        id: Long,
        name: String,
        isActive: Boolean
    ): Int {
        return componentDao.update(id, name, isActive)
    }

    override suspend fun deleteGroup(id: Long): Int {
        return groupDao.delete(id)
    }

    override suspend fun deleteComponent(id: Long): Int {
        onDeleteComponent(id, gamePlayerDao)
        return componentDao.delete(id)
    }
}
