package io.github.charliecpshaw.cluedo.data.model

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
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["player_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
        ForeignKey(
            entity = GamePlayer::class,
            parentColumns = ["id"],
            childColumns = ["target_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
        ForeignKey(
            entity = Place::class,
            parentColumns = ["id"],
            childColumns = ["death_place_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
        ForeignKey(
            entity = Weapon::class,
            parentColumns = ["id"],
            childColumns = ["death_weapon_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
    ],
)
data class GamePlayer(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    override val id: Long,

    @ColumnInfo(name = "game_id", index = true)
    val gameId: Long,

    @ColumnInfo(name = "player_id", index = true)
    val playerId: Long,

    @ColumnInfo(name = "is_alive")
    val isAlive: Boolean,

    @ColumnInfo(name = "target_id", index = true)
    val targetId: Long,

    @ColumnInfo(name = "death_place_id", index = true)
    val deathPlaceId: Long,

    @ColumnInfo(name = "death_weapon_id", index = true)
    val deathWeaponId: Long,
) : Identifiable