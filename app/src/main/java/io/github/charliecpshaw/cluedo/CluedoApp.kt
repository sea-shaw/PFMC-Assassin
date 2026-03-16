package io.github.charliecpshaw.cluedo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.charliecpshaw.cluedo.ui.screens.CluedoBottomAppBar
import io.github.charliecpshaw.cluedo.ui.navigation.CluedoNavHost
import io.github.charliecpshaw.cluedo.ui.navigation.GamesTabDestination

@Composable
fun CluedoApp(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
) {
  val startDestination = GamesTabDestination
  Scaffold(
    bottomBar = {
      CluedoBottomAppBar(
        navController = navController,
      )
    },
    modifier = modifier,
  ) { contentPadding ->
    CluedoNavHost(
      navController = navController,
      startDestination = startDestination,
      modifier = Modifier.padding(bottom = contentPadding.calculateBottomPadding())
    )
  }
}
