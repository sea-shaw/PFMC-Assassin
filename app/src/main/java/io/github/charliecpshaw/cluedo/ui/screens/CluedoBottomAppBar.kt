package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.charliecpshaw.cluedo.ui.navigation.topLevelDestinations

@Composable
fun CluedoBottomAppBar(
  navController: NavHostController,
) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  NavigationBar(
    windowInsets = NavigationBarDefaults.windowInsets,
  ) {
    topLevelDestinations.forEach { topLevelDestination ->
      CluedoNavigationBarItem(
        selected = currentDestination?.hierarchy?.any {
          it.hasRoute(topLevelDestination.destination::class)
        } ?: false,
        onClick = { alreadySelected ->
          navController.navigate(topLevelDestination.destination) {
            popUpTo(navController.graph.findStartDestination().id) {
              saveState = !alreadySelected
            }
            launchSingleTop = true
            restoreState = !alreadySelected
          }
        },
        icon = topLevelDestination.icon,
        contentDescriptionResId = topLevelDestination.nameResId,
      )
    }
  }
}

@Composable
private fun RowScope.CluedoNavigationBarItem(
  selected: Boolean,
  onClick: (Boolean) -> Unit,
  icon: ImageVector,
  @StringRes contentDescriptionResId: Int,
) {
  NavigationBarItem(
    selected = selected,
    onClick = { onClick(selected) },
    icon = {
      Icon(
        imageVector = icon,
        contentDescription = stringResource(contentDescriptionResId),
      )
    },
    label = {
      Text(
        text = stringResource(contentDescriptionResId),
      )
    },
  )
}

@Preview
@Composable
private fun CluedoBottomAppBarPreview() {
  CluedoBottomAppBar(
    navController = rememberNavController(),
  )
}
