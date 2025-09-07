package io.github.charliecpshaw.cluedo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "game",
    foreignKeys = [
        ForeignKey(
            entity = PlayerGroup::class,
            parentColumns = ["id"],
            childColumns = ["player_group_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
        ForeignKey(
            entity = PlaceGroup::class,
            parentColumns = ["id"],
            childColumns = ["place_group_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
        ForeignKey(
            entity = WeaponGroup::class,
            parentColumns = ["id"],
            childColumns = ["weapon_group_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE,
        ),
    ],
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long = 0,

    @ColumnInfo(name = "name")
    override val name: String,

    @ColumnInfo(name = "player_group_id", index = true)
    val playerGroupId: Long,

    @ColumnInfo(name = "place_group_id", index = true)
    val placeGroupId: Long,

    @ColumnInfo(name = "weapon_group_id", index = true)
    val weaponGroupId: Long,
) : Identifiable, Named
