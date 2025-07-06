package io.github.charliecpshaw.cluedo.data

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_player",
    foreignKeys = [
        ForeignKey(
            entity = Game::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["player_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["target_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = Place::class,
            parentColumns = ["id"],
            childColumns = ["death_place_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = Weapon::class,
            parentColumns = ["id"],
            childColumns = ["death_weapon_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
)
data class GamePlayer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "game_id")
    val gameId: Long,

    @ColumnInfo(name = "player_id")
    val playerId: Long,

    @ColumnInfo(name = "is_alive")
    val isAlive: Boolean,

    @ColumnInfo(name = "target_id")
    val targetId: Long?,

    @ColumnInfo(name = "death_place_id")
    val deathPlaceId: Long?,

    @ColumnInfo(name = "death_weapon_id")
    val deathWeaponId: Long?,
)
