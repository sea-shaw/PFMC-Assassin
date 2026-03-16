package io.github.charliecpshaw.cluedo.ui.viewmodels

import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.Weapon

interface ComponentDetails<C : Component, D : ComponentDetails<C, D>> {

  val name: String

  val isActive: Boolean

  fun toComponent(id: Long, groupId: Long): C

  fun isValid(): Boolean

  fun copyName(name: String): D

  fun copyIsActive(isActive: Boolean): D
}

data class PlayerDetails(
  override val name: String,
  val emailAddress: String,
  override val isActive: Boolean,
): ComponentDetails<Player, PlayerDetails> {
  override fun toComponent(
    id: Long,
    groupId: Long,
  ) = Player(
    id = id,
    name = name,
    emailAddress = emailAddress.ifBlank { null },
    groupId = groupId,
    isActive = isActive,
  )

  override fun isValid(): Boolean {
    return name.isNotBlank() && (emailAddress.isBlank() || android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())
  }

  override fun copyName(name: String): PlayerDetails {
    return copy(name = name)
  }

  fun copyEmailAddress(emailAddress: String): PlayerDetails {
    return copy(emailAddress = emailAddress)
  }

  override fun copyIsActive(isActive: Boolean): PlayerDetails {
    return copy(isActive = isActive)
  }
}

data class PlaceDetails(
  override val name: String,
  override val isActive: Boolean,
) : ComponentDetails<Place, PlaceDetails> {
  override fun toComponent(
    id: Long,
    groupId: Long,
  ) = Place(
    id = id,
    name = name,
    groupId = groupId,
    isActive = isActive,
  )

  override fun isValid(): Boolean {
    return name.isNotBlank()
  }

  override fun copyName(name: String): PlaceDetails {
    return copy(name = name)
  }

  override fun copyIsActive(isActive: Boolean): PlaceDetails {
    return copy(isActive = isActive)
  }
}

data class WeaponDetails(
  override val name: String,
  override val isActive: Boolean,
) : ComponentDetails<Weapon, WeaponDetails> {
  override fun toComponent(
    id: Long,
    groupId: Long,
  ): Weapon = Weapon(
    id = id,
    name = name,
    groupId = groupId,
    isActive = isActive
  )

  override fun isValid(): Boolean {
    return name.isNotBlank()
  }

  override fun copyName(name: String): WeaponDetails {
    return copy(name = name)
  }

  override fun copyIsActive(isActive: Boolean): WeaponDetails {
    return copy(isActive = isActive)
  }
}

interface ComponentDetailsFactory<C : Component, D : ComponentDetails<C, D>> {
  fun defaultDetails() : D
  fun toDetails(component: C): D
}

object PlayerDetailsFactory : ComponentDetailsFactory<Player, PlayerDetails> {
  override fun defaultDetails(): PlayerDetails {
    return PlayerDetails(
      name = "",
      emailAddress = "",
      isActive = true,
    )
  }

  override fun toDetails(component: Player): PlayerDetails = with(component) {
    return PlayerDetails(
      name = name,
      emailAddress = emailAddress ?: "",
      isActive = isActive,
    )
  }
}

object PlaceDetailsFactory : ComponentDetailsFactory<Place, PlaceDetails> {
  override fun defaultDetails(): PlaceDetails {
    return PlaceDetails(
      name = "",
      isActive = true,
    )
  }

  override fun toDetails(component: Place): PlaceDetails = with(component) {
    return PlaceDetails(
      name = name,
      isActive = isActive,
    )
  }
}

object WeaponDetailsFactory : ComponentDetailsFactory<Weapon, WeaponDetails> {
  override fun defaultDetails(): WeaponDetails {
    return WeaponDetails(
      name = "",
      isActive = true,
    )
  }

  override fun toDetails(component: Weapon): WeaponDetails = with(component) {
    return WeaponDetails(
      name = name,
      isActive = isActive,
    )
  }
}
