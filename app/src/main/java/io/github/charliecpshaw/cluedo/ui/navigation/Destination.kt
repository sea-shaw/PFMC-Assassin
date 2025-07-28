package io.github.charliecpshaw.cluedo.ui.navigation

sealed interface Destination {
    val route: String
}

sealed interface DetailDestination : Destination {
    companion object {
        const val ID_ARG = "id"
    }

    val routeWithArgs: String
        get() = "$route/{${EditDestination.Companion.ID_ARG}}"
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
    }
}

sealed interface ComponentEditDestination : EditDestination {
    object Player : EditDestination {
        override val route = "player_edit"
    }
}

sealed interface GroupEntryDestination : Destination {
    object Player : GroupEntryDestination {
        override val route = "player_group_entry"
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
}
