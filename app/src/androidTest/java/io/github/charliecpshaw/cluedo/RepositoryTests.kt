package io.github.charliecpshaw.cluedo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.charliecpshaw.cluedo.data.CluedoDatabase
import io.github.charliecpshaw.cluedo.data.dao.GameDao
import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflineGameRepository
import io.github.charliecpshaw.cluedo.data.dao.PlaceDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceGroupDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerGroupDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerInfoDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponGroupDao
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.data.repository.ComponentRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflinePlaceRepository
import io.github.charliecpshaw.cluedo.data.repository.OfflineWeaponRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RepositoryTests {
    private lateinit var cluedoDatabase: CluedoDatabase
    private lateinit var gameDao: GameDao
    private lateinit var gamePlayerDao: GamePlayerDao
    private lateinit var playerInfoDao: PlayerInfoDao
    private lateinit var playerGroupDao: PlayerGroupDao
    private lateinit var playerDao: PlayerDao
    private lateinit var placeGroupDao: PlaceGroupDao
    private lateinit var placeDao: PlaceDao
    private lateinit var weaponGroupDao: WeaponGroupDao
    private lateinit var weaponDao: WeaponDao

    private lateinit var gameRepository: GameRepository
    private lateinit var placeRepository: ComponentRepository<Place, PlaceGroup>
    private lateinit var weaponRepository: ComponentRepository<Weapon, WeaponGroup>

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        cluedoDatabase = Room.inMemoryDatabaseBuilder(context, CluedoDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        gameDao = cluedoDatabase.gameDao()
        gamePlayerDao = cluedoDatabase.gamePlayerDao()
        playerInfoDao = cluedoDatabase.playerInfoDao()
        playerGroupDao = cluedoDatabase.playerGroupDao()
        playerDao = cluedoDatabase.playerDao()
        placeGroupDao = cluedoDatabase.placeGroupDao()
        placeDao = cluedoDatabase.placeDao()
        weaponGroupDao = cluedoDatabase.weaponGroupDao()
        weaponDao = cluedoDatabase.weaponDao()

        gameRepository = OfflineGameRepository(
            gameDao = gameDao,
            gamePlayerDao = gamePlayerDao,
            playerInfoDao = playerInfoDao,
            playerDao = playerDao,
            placeDao = placeDao,
            weaponDao = weaponDao,
        )

        placeRepository = OfflinePlaceRepository(
            placeGroupDao = placeGroupDao,
            placeDao = placeDao,
        )

        weaponRepository = OfflineWeaponRepository(
            weaponGroupDao = weaponGroupDao,
            weaponDao = weaponDao,
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cluedoDatabase.close()
    }

    private suspend fun createTestGame(numPlayers: Int): Long {
        val playerGroupId = playerGroupDao.insert("Player Group")
        (0 until numPlayers).forEach {
            playerDao.insert(
                name = "Player $it",
                emailAddress = "player$it@email.com",
                groupId = playerGroupId,
                isActive = true,
            )
        }

        val placeGroupId = placeGroupDao.insert("Place Group")
        (0 until numPlayers).forEach {
            placeDao.insert(name = "Place $it", groupId = placeGroupId, isActive = true)
        }

        val weaponGroupId = weaponGroupDao.insert("Weapon Group")
        (0 until numPlayers).forEach {
            weaponDao.insert(name = "Weapon $it", groupId = weaponGroupId, isActive = true)
        }

        val gameId = gameRepository.createGame(
            name = "Game",
            playerGroupId = playerGroupId,
            placeGroupId = placeGroupId,
            weaponGroupId = weaponGroupId,
        )

        return gameId
    }

    @Test
    @Throws(Exception::class)
    fun createGame_validGameCreated() = runBlocking {
        val numPlayers = 3
        val gameId = createTestGame(numPlayers)
        val firstPlayerId = gameRepository.getAllAlivePlayersInGameStream(gameId).first()[0].playerId
        val placeIds = mutableSetOf<Long>()
        val weaponIds = mutableSetOf<Long>()
        var count = 0
        var playerId = firstPlayerId
        do {
            val gamePlayer = gameRepository.getPlayerStream(gameId, playerId).first()
            assertNotNull(gamePlayer)
            placeIds.add(gamePlayer!!.placeId)
            weaponIds.add(gamePlayer.weaponId)
            count++
            playerId = gamePlayer.targetId
        } while (playerId != firstPlayerId)
        assertEquals(numPlayers, count)
        assertEquals(numPlayers, placeIds.size)
        assertEquals(numPlayers, weaponIds.size)
    }

    @Test
    @Throws(Exception::class)
    fun killTarget_targetUpdated() = runBlocking {
        val numPlayers = 3
        val gameId = createTestGame(numPlayers)
        val player = gameRepository.getAllAlivePlayersInGameStream(gameId).first()[0]
        val target = gameRepository.getPlayerStream(gameId, player.targetId).first()
        assertNotNull(target)
        val newTarget = gameRepository.getPlayerStream(gameId, target!!.targetId).first()
        assertNotNull(newTarget)

        gameRepository.killTarget(gameId, player.playerId)

        val updatedPlayer = gameRepository.getPlayerStream(gameId, player.playerId).first()
        assertNotNull(updatedPlayer)
        assertEquals(newTarget!!.playerId, updatedPlayer!!.targetId)

        val updatedTarget = gameRepository.getPlayerStream(gameId, target.playerId).first()
        assertNull(updatedTarget)

        val updatedAlivePlayers = gameRepository.getAllAlivePlayersInGameStream(gameId).first()
        assertEquals(numPlayers - 1, updatedAlivePlayers.size)
    }

    @Test
    @Throws(Exception::class)
    fun deletePlace_replacedWithRandom() = runBlocking {
        val numPlayers = 3
        val gameId = createTestGame(numPlayers)
        val player = gameRepository.getAllAlivePlayersInGameStream(gameId).first()[0]

        placeRepository.deleteComponent(player.placeId)

        val updatedPlayers = gameRepository.getAllAlivePlayersInGameStream(gameId).first()
        assertEquals(numPlayers, updatedPlayers.size)
        updatedPlayers.forEach {
            assertNotEquals(player.placeId, it.placeId)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteWeapon_replacedWithRandom() = runBlocking {
        val numPlayers = 3
        val gameId = createTestGame(numPlayers)
        val player = gameRepository.getAllAlivePlayersInGameStream(gameId).first()[0]

        weaponRepository.deleteComponent(player.weaponId)

        val updatedPlayers = gameRepository.getAllAlivePlayersInGameStream(gameId).first()
        assertEquals(numPlayers, updatedPlayers.size)
        updatedPlayers.forEach {
            assertNotEquals(player.weaponId, it.weaponId)
        }
    }
}
