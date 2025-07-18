package io.github.charliecpshaw.cluedo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
    version = 1,
    exportSchema = false,
)
abstract class CluedoDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun gamePlayerDao(): GamePlayerDao
    abstract fun placeDao(): PlaceDao
    abstract fun placeGroupDao(): PlaceGroupDao
    abstract fun playerDao(): PlayerDao
    abstract fun playerGroupDao(): PlayerGroupDao
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
