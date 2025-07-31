package io.github.charliecpshaw.cluedo.ui.navigation

enum class Tab { Games, Players, Places, Weapons }

sealed interface Destination {
    val route: String

    object Games : Destination {
        override val route = "games"
    }

    object Game : Destination {

        const val GAME_ID_ARG = "game_id"

        override val route = "game"

        val routeWithArgs = "$route/{$GAME_ID_ARG}"
    }

    object GamePlayer : Destination {
        const val GAME_ID_ARG = "game_id"
        const val PLAYER_ID_ARG = "player_id"
        override val route = "game_player"
        val routeWithArgs = "$route/{$GAME_ID_ARG}/{$PLAYER_ID_ARG}"
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

sealed interface GameEntryDestination : Destination {
    companion object {
        const val PLAYER_GROUP_ID_ARG = "player_group_id"
        const val PLACE_GROUP_ID_ARG = "place_group_id"
        const val WEAPON_GROUP_ID_ARG = "weapon_group_id"
    }

    object Players : GameEntryDestination {
        override val route = "game_entry_player_group"
    }

    object Places : GameEntryDestination {

        override val route = "game_entry_place_group"

        val routeWithArgs: String
            get() = "$route/{$PLAYER_GROUP_ID_ARG}"
    }

    object Weapons : GameEntryDestination {

        override val route = "game_entry_weapon_group"

        val routeWithArgs: String
            get() = "$route/{$PLAYER_GROUP_ID_ARG}/{$PLACE_GROUP_ID_ARG}"
    }

    object Name : GameEntryDestination {

        override val route = "game_entry_name"

        val routeWithArgs: String
            get() = "$route/{$PLAYER_GROUP_ID_ARG}/{$PLACE_GROUP_ID_ARG}/{$WEAPON_GROUP_ID_ARG}"
    }
}
