package io.github.charliecpshaw.cluedo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                navigateToPlayerGroupEntry = {},
                navigateToPlayerGroup = {},
            )
        }
    }
}
