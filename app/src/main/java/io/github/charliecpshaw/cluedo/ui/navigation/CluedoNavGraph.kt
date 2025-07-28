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
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.ui.screens.GroupScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupsScreen
import io.github.charliecpshaw.cluedo.ui.screens.PlayerEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.PlayerEntryScreen
import io.github.charliecpshaw.cluedo.ui.screens.PlayerGroupEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.PlayerGroupEntryScreen

@Composable
fun CluedoNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(route = GroupsDestination.Player.route) {
            GroupsScreen<Player, PlayerGroup>(
                titleResId = R.string.player_groups_title,
                navigateToGroupEntry = {
                    navController.navigate(GroupEntryDestination.Player.route)
                },
                navigateToGroup = {
                    navController.navigate("${GroupDestination.Player.route}/$it")
                },
            )
        }
        composable(route = GroupEntryDestination.Player.route) {
            PlayerGroupEntryScreen(
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
            GroupScreen<Player, PlayerGroup>(
                editContentDescriptionResId = R.string.player_group_edit_title,
                deleteContentDescriptionResId = R.string.player_group_delete,
                componentEntryContentDescriptionResId = R.string.player_entry_title,
                deleteQuestionResId = R.string.player_group_delete_question,
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
            route = ComponentEntryDestination.Player.routeWithArgs,
            arguments = listOf(
                navArgument(name = ComponentEntryDestination.GROUP_ID_ARG) { type = NavType.LongType },
            )
        ) {
            PlayerEntryScreen(
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
            PlayerEditScreen(
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
            PlayerGroupEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}
