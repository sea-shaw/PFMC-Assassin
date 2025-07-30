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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.charliecpshaw.cluedo.ui.icons.Camping
import io.github.charliecpshaw.cluedo.ui.icons.Person
import io.github.charliecpshaw.cluedo.ui.icons.Swords
import io.github.charliecpshaw.cluedo.ui.icons.Trophy
import io.github.charliecpshaw.cluedo.ui.navigation.CluedoNavHost
import io.github.charliecpshaw.cluedo.ui.navigation.Destination
import io.github.charliecpshaw.cluedo.ui.navigation.GroupsDestination
import io.github.charliecpshaw.cluedo.ui.navigation.Tab
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme

@Composable
fun CluedoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val startDestination = Destination.Games
    val startTab = Tab.Games
    Scaffold(
        bottomBar = {
            CluedoBottomAppBar(
                navController = navController,
                tab = startTab,
            )
        },
        modifier = modifier,
    ) { contentPadding ->
        CluedoNavHost(
            navController = navController,
            startDestinationRoute = startDestination.route,
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
    tab: Tab,
) {
    var selectedTabOrdinal by rememberSaveable { mutableIntStateOf(tab.ordinal) }

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
    ) {
        CluedoNavigationBarItem(
            selected = selectedTabOrdinal == Tab.Games.ordinal,
            onClick = {
                navController.navigate(route = Destination.Games.route)
                selectedTabOrdinal = Tab.Games.ordinal
            },
            imageVector = Trophy,
            contentDescription = "Games",
        )
        CluedoNavigationBarItem(
            selected = selectedTabOrdinal == Tab.Players.ordinal,
            onClick = {
                navController.navigate(route = GroupsDestination.Player.route)
                selectedTabOrdinal = Tab.Players.ordinal
            },
            imageVector = Person,
            contentDescription = "Players",
        )
        CluedoNavigationBarItem(
            selected = selectedTabOrdinal == Tab.Places.ordinal,
            onClick = {
                navController.navigate(route = GroupsDestination.Place.route)
                selectedTabOrdinal = Tab.Places.ordinal
            },
            imageVector = Camping,
            contentDescription = "Places",
        )
        CluedoNavigationBarItem(
            selected = selectedTabOrdinal == Tab.Weapons.ordinal,
            onClick = {
                navController.navigate(route = GroupsDestination.Weapon.route)
                selectedTabOrdinal = Tab.Weapons.ordinal
            },
            imageVector = Swords,
            contentDescription = "Weapons",
        )
    }
}

@Composable
private fun RowScope.CluedoNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
            )
        },
        label = {
            Text(
                text = contentDescription,
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
        tab = Tab.Players,
    )
}
