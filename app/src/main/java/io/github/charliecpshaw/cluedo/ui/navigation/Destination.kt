package io.github.charliecpshaw.cluedo.ui.navigation

enum class Tab { Games, Players, Places, Weapons }

sealed interface Destination {
    val route: String
    val tab: Tab
}

sealed interface GroupsDestination : Destination {
    object Player : GroupsDestination {
        override val route = "player_groups"
        override val tab = Tab.Players
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
        override val tab = Tab.Players
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
    object Player : EditDestination {
        override val route = "player_group_edit"
        override val tab = Tab.Players
    }
}

sealed interface ComponentEditDestination : EditDestination {
    object Player : EditDestination {
        override val route = "player_edit"
        override val tab = Tab.Players
    }
}

sealed interface GroupEntryDestination : Destination {
    object Player : GroupEntryDestination {
        override val route = "player_group_entry"
        override val tab = Tab.Players
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
        override val tab = Tab.Players
    }
}
