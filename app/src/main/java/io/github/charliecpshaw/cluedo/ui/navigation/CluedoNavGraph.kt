package io.github.charliecpshaw.cluedo.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.charliecpshaw.cluedo.ui.PlayerGroup
import io.github.charliecpshaw.cluedo.ui.PlayerGroupDestination
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
                    navController.navigate("${PlayerGroupDestination.route}/${it}")
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
                navArgument(PlayerGroupDestination.GROUP_ID_ARG) { type = NavType.LongType },
            )
        ) {
            PlayerGroupScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { /* TODO */ },
                navigateToPlayerEdit = { /* TODO */ },
                navigateToPlayerEntry = { /* TODO */ },
            )
        }
    }
}
