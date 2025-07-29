package io.github.charliecpshaw.cluedo.ui.navigation

enum class Tab { Games, Players, Places, Weapons }

sealed interface Destination {
    val route: String

    object Games : Destination {
        override val route = "games"
    }
}

sealed interface GroupsDestination : Destination {
    object Player : GroupsDestination {
        override val route = "player_groups"
    }

    object Place : GroupsDestination {
        override val route = "place_groups"
    }

    object Weapon : GroupsDestination {
        override val route = "weapon_groups"
    }
}

sealed interface GroupDestination : Destination {
    companion object {
        const val ID_ARG = "id"
    }

    val routeWithArgs: String
        get() = "$route/{${ID_ARG}}"

    object Player : GroupDestination {
        override val route = "player_group"
    }

    object Place : GroupDestination {
        override val route = "place_group"
    }

    object Weapon : GroupDestination {
        override val route = "weapon_group"
    }
}

sealed interface EditDestination : Destination {
    companion object {
        const val ID_ARG = "id"
    }

    val routeWithArgs: String
        get() = "$route/{$ID_ARG}"
}

sealed interface GroupEditDestination : EditDestination {
    object Player : GroupEditDestination {
        override val route = "player_group_edit"
    }

    object Place : GroupEditDestination {
        override val route = "place_group_edit"
    }

    object Weapon : GroupEditDestination {
        override val route = "weapon_group_edit"
    }
}

sealed interface ComponentEditDestination : EditDestination {
    object Player : ComponentEditDestination {
        override val route = "player_edit"
    }

    object Place : ComponentEditDestination {
        override val route = "place_edit"
    }

    object Weapon : ComponentEditDestination {
        override val route = "weapon_edit"
    }
}

sealed interface GroupEntryDestination : Destination {
    object Player : GroupEntryDestination {
        override val route = "player_group_entry"
    }

    object Place : GroupEntryDestination {
        override val route = "place_group_entry"
    }

    object Weapon : GroupEntryDestination {
        override val route = "weapon_group_entry"
    }
}

sealed interface ComponentEntryDestination : Destination {

    companion object {
        const val GROUP_ID_ARG = "group_id"
    }

    val routeWithArgs: String
        get() = "$route/{$GROUP_ID_ARG}"

    object Player : ComponentEntryDestination {
        override val route = "player_entry"
    }

    object Place : ComponentEntryDestination {
        override val route = "place_entry"
    }

    object Weapon : ComponentEntryDestination {
        override val route = "weapon_entry"
    }
}
