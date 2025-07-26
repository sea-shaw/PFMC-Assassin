package io.github.charliecpshaw.cluedo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.charliecpshaw.cluedo.ui.PlayerEditDestination
import io.github.charliecpshaw.cluedo.ui.PlayerEditScreen
import io.github.charliecpshaw.cluedo.ui.PlayerEntryDestination
import io.github.charliecpshaw.cluedo.ui.PlayerEntryScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupDestination
import io.github.charliecpshaw.cluedo.ui.PlayerGroupEditDestination
import io.github.charliecpshaw.cluedo.ui.PlayerGroupEditScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupEntryDestination
import io.github.charliecpshaw.cluedo.ui.PlayerGroupEntryScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupScreen
import io.github.charliecpshaw.cluedo.ui.PlayerGroupsDestination
import io.github.charliecpshaw.cluedo.ui.PlayerGroupsScreen

@Composable
fun CluedoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = PlayerGroupsDestination.route,
        modifier = modifier,
    ) {
        composable(route = PlayerGroupsDestination.route) {
            PlayerGroupsScreen(
                navigateToPlayerGroupEntry = {
                    navController.navigate(PlayerGroupEntryDestination.route)
                },
                navigateToPlayerGroup = {
                    navController.navigate("${PlayerGroupDestination.route}/$it")
                },
            )
        }
        composable(route = PlayerGroupEntryDestination.route) {
            PlayerGroupEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = PlayerGroupDestination.routeWithArgs,
            arguments = listOf(
                navArgument(name = PlayerGroupDestination.GROUP_ID_ARG) { type = NavType.LongType },
            )
        ) {
            PlayerGroupScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEdit = {
                    navController.navigate(route = "${PlayerGroupEditDestination.route}/$it")
                },
                navigateToPlayerEdit = {
                    navController.navigate(route = "${PlayerEditDestination.route}/$it")
                },
                navigateToPlayerEntry = {
                    navController.navigate(route = "${PlayerEntryDestination.route}/$it")
                },
            )
        }
        composable(
            route = PlayerEntryDestination.routeWithArgs,
            arguments = listOf(
                navArgument(name = PlayerEntryDestination.GROUP_ID_ARG) { type = NavType.LongType },
            )
        ) {
            PlayerEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = PlayerEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(name = PlayerEditDestination.PLAYER_ID_ARG) { type = NavType.LongType },
            )
        ) {
            PlayerEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = PlayerGroupEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(name = PlayerGroupEditDestination.GROUP_ID_ARG) {
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
