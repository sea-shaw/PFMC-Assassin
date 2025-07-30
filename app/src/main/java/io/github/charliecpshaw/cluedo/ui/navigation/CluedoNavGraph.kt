package io.github.charliecpshaw.cluedo.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.ui.screens.ComponentEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.ComponentEntryScreen
import io.github.charliecpshaw.cluedo.ui.screens.GamesScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupEntryScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupSelectionScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupsScreen
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupSelectionViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupsViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupSelectionViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupsViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponGroupEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponGroupEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponGroupSelectionViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponGroupViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponGroupsViewModel

@Composable
fun CluedoNavHost(
    navController: NavHostController,
    startDestinationRoute: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(route = GroupsDestination.Player.route) {
            GroupsScreen<PlayerGroupsViewModel, Player, PlayerGroup>(
                titleResId = R.string.player_groups_title,
                navigateToGroupEntry = {
                    navController.navigate(GroupEntryDestination.Player.route)
                },
                navigateToGroup = {
                    navController.navigate("${GroupDestination.Player.route}/$it")
                },
            )
        }
        composable(route = GroupsDestination.Place.route) {
            GroupsScreen<PlaceGroupsViewModel, Place, PlaceGroup>(
                titleResId = R.string.place_groups_title,
                navigateToGroupEntry = {
                    navController.navigate(GroupEntryDestination.Place.route)
                },
                navigateToGroup = {
                    navController.navigate("${GroupDestination.Place.route}/$it")
                },
            )
        }
        composable(route = GroupsDestination.Weapon.route) {
            GroupsScreen<WeaponGroupsViewModel, Weapon, WeaponGroup>(
                titleResId = R.string.weapon_groups_title,
                navigateToGroupEntry = {
                    navController.navigate(GroupEntryDestination.Weapon.route)
                },
                navigateToGroup = {
                    navController.navigate("${GroupDestination.Weapon.route}/$it")
                },
            )
        }

        composable(route = GroupEntryDestination.Player.route) {
            GroupEntryScreen<PlayerGroupEntryViewModel, Player, PlayerGroup>(
                titleResId = R.string.player_group_entry_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(route = GroupEntryDestination.Place.route) {
            GroupEntryScreen<PlaceGroupEntryViewModel, Place, PlaceGroup>(
                titleResId = R.string.place_group_entry_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(route = GroupEntryDestination.Weapon.route) {
            GroupEntryScreen<WeaponGroupEntryViewModel, Weapon, WeaponGroup>(
                titleResId = R.string.weapon_group_entry_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(
            route = GroupDestination.Player.routeWithArgs,
            arguments = listOf(
                navArgument(name = GroupDestination.ID_ARG) { type = NavType.LongType },
            )
        ) {
            GroupScreen<PlayerGroupViewModel, Player, PlayerGroup>(
                editContentDescriptionResId = R.string.player_group_edit_title,
                deleteContentDescriptionResId = R.string.player_group_delete,
                componentEntryContentDescriptionResId = R.string.player_entry_title,
                deleteQuestionResId = R.string.group_delete_question,
                navigateBack = { navController.navigateUp() },
                navigateToGroupEdit = {
                    navController.navigate(route = "${GroupEditDestination.Player.route}/$it")
                },
                navigateToComponentEdit = {
                    navController.navigate(route = "${ComponentEditDestination.Player.route}/$it")
                },
                navigateToComponentEntry = {
                    navController.navigate(route = "${ComponentEntryDestination.Player.route}/$it")
                },
            )
        }
        composable(
            route = GroupDestination.Place.routeWithArgs,
            arguments = listOf(
                navArgument(name = GroupDestination.ID_ARG) { type = NavType.LongType },
            )
        ) {
            GroupScreen<PlaceGroupViewModel, Place, PlaceGroup>(
                editContentDescriptionResId = R.string.place_group_edit_title,
                deleteContentDescriptionResId = R.string.place_group_delete,
                componentEntryContentDescriptionResId = R.string.place_entry_title,
                deleteQuestionResId = R.string.group_delete_question,
                navigateBack = { navController.navigateUp() },
                navigateToGroupEdit = {
                    navController.navigate(route = "${GroupEditDestination.Place.route}/$it")
                },
                navigateToComponentEdit = {
                    navController.navigate(route = "${ComponentEditDestination.Place.route}/$it")
                },
                navigateToComponentEntry = {
                    navController.navigate(route = "${ComponentEntryDestination.Place.route}/$it")
                },
            )
        }
        composable(
            route = GroupDestination.Weapon.routeWithArgs,
            arguments = listOf(
                navArgument(name = GroupDestination.ID_ARG) { type = NavType.LongType },
            )
        ) {
            GroupScreen<WeaponGroupViewModel, Weapon, WeaponGroup>(
                editContentDescriptionResId = R.string.weapon_group_edit_title,
                deleteContentDescriptionResId = R.string.weapon_group_delete,
                componentEntryContentDescriptionResId = R.string.weapon_entry_title,
                deleteQuestionResId = R.string.group_delete_question,
                navigateBack = { navController.navigateUp() },
                navigateToGroupEdit = {
                    navController.navigate(route = "${GroupEditDestination.Weapon.route}/$it")
                },
                navigateToComponentEdit = {
                    navController.navigate(route = "${ComponentEditDestination.Weapon.route}/$it")
                },
                navigateToComponentEntry = {
                    navController.navigate(route = "${ComponentEntryDestination.Weapon.route}/$it")
                },
            )
        }

        composable(
            route = ComponentEntryDestination.Player.routeWithArgs,
            arguments = listOf(
                navArgument(name = ComponentEntryDestination.GROUP_ID_ARG) {
                    type = NavType.LongType
                },
            )
        ) {
            ComponentEntryScreen<PlayerEntryViewModel, Player, PlayerGroup>(
                titleResId = R.string.player_entry_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = ComponentEntryDestination.Place.routeWithArgs,
            arguments = listOf(
                navArgument(name = ComponentEntryDestination.GROUP_ID_ARG) {
                    type = NavType.LongType
                },
            )
        ) {
            ComponentEntryScreen<PlaceEntryViewModel, Place, PlaceGroup>(
                titleResId = R.string.place_entry_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = ComponentEntryDestination.Weapon.routeWithArgs,
            arguments = listOf(
                navArgument(name = ComponentEntryDestination.GROUP_ID_ARG) {
                    type = NavType.LongType
                },
            )
        ) {
            ComponentEntryScreen<WeaponEntryViewModel, Weapon, WeaponGroup>(
                titleResId = R.string.weapon_entry_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(
            route = ComponentEditDestination.Player.routeWithArgs,
            arguments = listOf(
                navArgument(name = EditDestination.ID_ARG) { type = NavType.LongType },
            )
        ) {
            ComponentEditScreen<PlayerEditViewModel, Player, PlayerGroup>(
                titleResId = R.string.player_edit_title,
                deleteContentDescriptionResId = R.string.player_delete,
                deleteQuestionResId = R.string.player_delete_question,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = ComponentEditDestination.Place.routeWithArgs,
            arguments = listOf(
                navArgument(name = EditDestination.ID_ARG) { type = NavType.LongType },
            )
        ) {
            ComponentEditScreen<PlaceEditViewModel, Place, PlaceGroup>(
                titleResId = R.string.place_edit_title,
                deleteContentDescriptionResId = R.string.place_delete,
                deleteQuestionResId = R.string.place_delete_question,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = ComponentEditDestination.Weapon.routeWithArgs,
            arguments = listOf(
                navArgument(name = EditDestination.ID_ARG) { type = NavType.LongType },
            )
        ) {
            ComponentEditScreen<WeaponEditViewModel, Weapon, WeaponGroup>(
                titleResId = R.string.weapon_edit_title,
                deleteContentDescriptionResId = R.string.weapon_delete,
                deleteQuestionResId = R.string.weapon_delete_question,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(
            route = GroupEditDestination.Player.routeWithArgs,
            arguments = listOf(
                navArgument(name = EditDestination.ID_ARG) {
                    type = NavType.LongType
                },
            )
        ) {
            GroupEditScreen<PlayerGroupEditViewModel, Player, PlayerGroup>(
                titleResId = R.string.player_group_edit_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = GroupEditDestination.Place.routeWithArgs,
            arguments = listOf(
                navArgument(name = EditDestination.ID_ARG) {
                    type = NavType.LongType
                },
            )
        ) {
            GroupEditScreen<PlaceGroupEditViewModel, Place, PlaceGroup>(
                titleResId = R.string.place_group_edit_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = GroupEditDestination.Weapon.routeWithArgs,
            arguments = listOf(
                navArgument(name = EditDestination.ID_ARG) {
                    type = NavType.LongType
                },
            )
        ) {
            GroupEditScreen<WeaponGroupEditViewModel, Weapon, WeaponGroup>(
                titleResId = R.string.weapon_group_edit_title,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(
            route = Destination.Games.route,
        ) {
            GamesScreen(
                navigateToGame = {},
                navigateToGameEntry = { navController.navigate(GameEntryDestination.Players.route) },
            )
        }
        composable(
            route = GameEntryDestination.Players.route,
        ) {
            GroupSelectionScreen<PlayerGroupSelectionViewModel, Player, PlayerGroup>(
                titleResId = R.string.game_player_group_select,
                onGroupClick = { playerGroupId ->
                    navController.navigate("${GameEntryDestination.Places.route}/$playerGroupId")
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = GameEntryDestination.Places.routeWithArgs,
        ) {
            GroupSelectionScreen<PlaceGroupSelectionViewModel, Place, PlaceGroup>(
                titleResId = R.string.game_place_group_select,
                onGroupClick = { placeGroupId ->
                    val backStackEntry = navController.currentBackStackEntry!!
                    val args = backStackEntry.arguments!!
                    val playerGroupId = args.getString(GameEntryDestination.PLAYER_GROUP_ID_ARG)
                    navController.navigate("${GameEntryDestination.Weapons.route}/$playerGroupId/$placeGroupId")
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = GameEntryDestination.Weapons.routeWithArgs,
        ) {
            GroupSelectionScreen<WeaponGroupSelectionViewModel, Weapon, WeaponGroup>(
                titleResId = R.string.game_weapon_group_select,
                onGroupClick = { weaponGroupId ->
                    val backStackEntry = navController.currentBackStackEntry!!
                    val args = backStackEntry.arguments!!
                    val playerGroupId = args.getString(GameEntryDestination.PLAYER_GROUP_ID_ARG)
                    val placeGroupId = args.getString(GameEntryDestination.PLACE_GROUP_ID_ARG)
                    navController.navigate("${GameEntryDestination.Name.route}/$playerGroupId/$placeGroupId/$weaponGroupId")
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
//        composable(
//            route = GameEntryDestination.Name.routeWithArgs,
//        ) {
//
//        }
    }
}
