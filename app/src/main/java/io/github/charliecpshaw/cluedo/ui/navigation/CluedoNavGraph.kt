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
import io.github.charliecpshaw.cluedo.ui.PlayerEditScreen
import io.github.charliecpshaw.cluedo.ui.PlayerEntryScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupEditScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupEntryScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupsScreen

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
            PlayerGroupsScreen(
                navigateToPlayerGroupEntry = {
                    navController.navigate(GroupEntryDestination.Player.route)
                },
                navigateToPlayerGroup = {
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
            PlayerGroupScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEdit = {
                    navController.navigate(route = "${GroupEditDestination.Player.route}/$it")
                },
                navigateToPlayerEdit = {
                    navController.navigate(route = "${ComponentEditDestination.Player.route}/$it")
                },
                navigateToPlayerEntry = {
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
