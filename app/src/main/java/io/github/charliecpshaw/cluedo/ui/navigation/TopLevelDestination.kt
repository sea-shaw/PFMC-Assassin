package io.github.charliecpshaw.cluedo.ui.navigation

import androidx.annotation.StringRes
import io.github.charliecpshaw.cluedo.R
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.charliecpshaw.cluedo.ui.icons.Camping
import io.github.charliecpshaw.cluedo.ui.icons.Person
import io.github.charliecpshaw.cluedo.ui.icons.Swords
import io.github.charliecpshaw.cluedo.ui.icons.Trophy

data class TopLevelDestination<T : Any>(
    val nameResId: Int,
    val destination: T,
    val icon: ImageVector,
)

val topLevelDestinations = listOf(
    TopLevelDestination(R.string.games_tab, GamesTabDestination, Trophy),
    TopLevelDestination(R.string.players_tab, PlayersTabDestination, Person),
    TopLevelDestination(R.string.places_tab, PlacesTabDestination, Camping),
    TopLevelDestination(R.string.weapons_tab, WeaponsTabDestination, Swords),
)
