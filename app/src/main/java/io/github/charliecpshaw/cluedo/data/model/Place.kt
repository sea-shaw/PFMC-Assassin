package io.github.charliecpshaw.cluedo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  tableName = "place",
  foreignKeys = [
    ForeignKey(
      entity = PlaceGroup::class,
      parentColumns = ["id"],
      childColumns = ["group_id"],
      onUpdate = ForeignKey.CASCADE,
      onDelete = ForeignKey.CASCADE,
    ),
  ],
)
data class Place(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  override val id: Long = 0,

  @ColumnInfo(name = "name")
  override val name: String,

  @ColumnInfo(name = "group_id", index = true)
  override val groupId: Long,

  @ColumnInfo(name = "is_active")
  override val isActive: Boolean,
) : Component
