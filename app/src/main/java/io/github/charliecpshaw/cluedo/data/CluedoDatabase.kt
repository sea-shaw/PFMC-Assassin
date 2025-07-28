package io.github.charliecpshaw.cluedo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.charliecpshaw.cluedo.data.dao.GameDao
import io.github.charliecpshaw.cluedo.data.dao.GamePlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceDao
import io.github.charliecpshaw.cluedo.data.dao.PlaceGroupDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerGroupDao
import io.github.charliecpshaw.cluedo.data.dao.PlayerInfoDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponDao
import io.github.charliecpshaw.cluedo.data.dao.WeaponGroupDao
import io.github.charliecpshaw.cluedo.data.model.Game
import io.github.charliecpshaw.cluedo.data.model.GamePlayer
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup

private const val CURRENT_VERSION = 3

@Database(
    entities = [
        Game::class,
        GamePlayer::class,
        Place::class,
        PlaceGroup::class,
        Player::class,
        PlayerGroup::class,
        Weapon::class,
        WeaponGroup::class,
    ],
    version = CURRENT_VERSION,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class CluedoDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun gamePlayerDao(): GamePlayerDao
    abstract fun placeDao(): PlaceDao
    abstract fun placeGroupDao(): PlaceGroupDao
    abstract fun playerDao(): PlayerDao
    abstract fun playerGroupDao(): PlayerGroupDao

    abstract fun playerInfoDao(): PlayerInfoDao

    abstract fun weaponDao(): WeaponDao
    abstract fun weaponGroupDao(): WeaponGroupDao

    companion object {
        @Volatile
        private var Instance: CluedoDatabase? = null

        fun getDatabase(context: Context): CluedoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    CluedoDatabase::class.java,
                    "cluedo_database"
                ).fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
