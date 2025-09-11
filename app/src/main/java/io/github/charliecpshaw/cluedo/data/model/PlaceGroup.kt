package io.github.charliecpshaw.cluedo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_group")
data class PlaceGroup(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long = 0,

    @ColumnInfo(name = "name")
    override val name: String,
) : Group<Place>
