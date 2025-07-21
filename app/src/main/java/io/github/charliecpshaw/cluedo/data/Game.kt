package io.github.charliecpshaw.cluedo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "game",
    foreignKeys = [
        ForeignKey(
            entity = PlayerGroup::class,
            parentColumns = ["id"],
            childColumns = ["player_group_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = PlaceGroup::class,
            parentColumns = ["id"],
            childColumns = ["place_group_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = WeaponGroup::class,
            parentColumns = ["id"],
            childColumns = ["weapon_group_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "start_date")
    val start: Instant,

    @ColumnInfo(name = "end_date")
    val end: Instant?,

    @ColumnInfo(name = "player_group_id", index = true)
    val playerGroupId: Long,

    @ColumnInfo(name = "place_group_id", index = true)
    val placeGroupId: Long,

    @ColumnInfo(name = "weapon_group_id", index = true)
    val weaponGroupId: Long,
)
