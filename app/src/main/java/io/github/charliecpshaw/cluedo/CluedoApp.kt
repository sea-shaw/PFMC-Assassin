package io.github.charliecpshaw.cluedo

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.charliecpshaw.cluedo.ui.navigation.CluedoNavHost
import io.github.charliecpshaw.cluedo.ui.navigation.GamesTabDestination
import io.github.charliecpshaw.cluedo.ui.navigation.topLevelDestinations
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CluedoTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigateUp: () -> Unit = {},
    hasEditButton: Boolean = false,
    onEditClick: () -> Unit = {},
    @StringRes editContentDescriptionRes: Int = 0,
    hasDeleteButton: Boolean = false,
    onDeleteClick: () -> Unit = {},
    @StringRes deleteContentDescriptionResId: Int = 0,
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (hasEditButton) {
                IconButton(
                    onClick = onEditClick,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = editContentDescriptionRes),
                    )
                }
            }
            if (hasDeleteButton) {
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = deleteContentDescriptionResId),
                    )
                }
            }
        }
    )
}

@Composable
private fun CluedoBottomAppBar(
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
                onClick = {
                    navController.navigate(topLevelDestination.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
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
    onClick: () -> Unit,
    icon: ImageVector,
    @StringRes contentDescriptionResId: Int,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CluedoTopAppBarPreview() {
    CluedoTheme {
        CluedoTopAppBar(
            title = "Title",
            canNavigateBack = true,
            hasEditButton = true,
            hasDeleteButton = true,
        )
    }
}

@Preview
@Composable
private fun CluedoBottomAppBarPreview() {
    CluedoBottomAppBar(
        navController = rememberNavController(),
    )
}
