package io.github.charliecpshaw.cluedo.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Place
import io.github.charliecpshaw.cluedo.data.model.PlaceGroup
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.data.model.PlayerGroup
import io.github.charliecpshaw.cluedo.data.model.Weapon
import io.github.charliecpshaw.cluedo.data.model.WeaponGroup
import io.github.charliecpshaw.cluedo.ui.screens.ComponentEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.ComponentEntryScreen
import io.github.charliecpshaw.cluedo.ui.screens.GameEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.GameEntryScreen
import io.github.charliecpshaw.cluedo.ui.screens.GamePlayerScreen
import io.github.charliecpshaw.cluedo.ui.screens.GameScreen
import io.github.charliecpshaw.cluedo.ui.screens.GamesScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupEditScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupEntryScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupSelectionScreen
import io.github.charliecpshaw.cluedo.ui.screens.GroupsScreen
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceDetails
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupSelectionViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlaceGroupsViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerDetails
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupEditViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupSelectionViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerGroupsViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.WeaponDetails
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
    startDestination: Any,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        navigation<GamesTabDestination>(startDestination = GamesDestination) {
            composable<GamesDestination> {
                GamesScreen(
                    navigateToGame = { navController.navigate(route = GameDestination(id = it)) },
                    navigateToGameEntry = {
                        navController.navigate(route = GameEntryDestination)
                    },
                )
            }

            navigation<GameEntryDestination>(startDestination = GamePlayerGroupSelectionDestination) {
                composable<GamePlayerGroupSelectionDestination> {
                    GroupSelectionScreen<PlayerGroupSelectionViewModel, Player, PlayerGroup>(
                        titleResId = R.string.game_player_group_select,
                        onGroupClick = { playerGroupId ->
                            navController.navigate(
                                route = GamePlaceGroupSelectionDestination(playerGroupId = playerGroupId)
                            )
                        },
                        onNavigateUp = { navController.navigateUp() },
                    )
                }
                composable<GamePlaceGroupSelectionDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<GamePlaceGroupSelectionDestination>()
                    GroupSelectionScreen<PlaceGroupSelectionViewModel, Place, PlaceGroup>(
                        titleResId = R.string.game_place_group_select,
                        onGroupClick = { placeGroupId ->
                            navController.navigate(
                                route = GameWeaponGroupSelectionDestination(
                                    playerGroupId = destination.playerGroupId,
                                    placeGroupId = placeGroupId,
                                )
                            )
                        },
                        onNavigateUp = { navController.navigateUp() },
                    )
                }
                composable<GameWeaponGroupSelectionDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<GameWeaponGroupSelectionDestination>()
                    GroupSelectionScreen<WeaponGroupSelectionViewModel, Weapon, WeaponGroup>(
                        titleResId = R.string.game_weapon_group_select,
                        onGroupClick = { weaponGroupId ->
                            navController.navigate(
                                route = GameNameEntryDestination(
                                    playerGroupId = destination.playerGroupId,
                                    placeGroupId = destination.placeGroupId,
                                    weaponGroupId = weaponGroupId,
                                )
                            )
                        },
                        onNavigateUp = { navController.navigateUp() },
                    )
                }
                composable<GameNameEntryDestination> {
                    GameEntryScreen(
                        navigateBack = {
                            navController.popBackStack(route = GamesDestination, inclusive = false)
                        },
                        onNavigateUp = { navController.navigateUp() }
                    )
                }
            }
            composable<GameDestination> {
                GameScreen(
                    onNavigateUp = { navController.navigateUp() },
                    navigateBack = { navController.popBackStack() },
                    navigateToEdit = { gameId ->
                        navController.navigate(route = GameEditDestination(id = gameId))
                    },
                    navigateToPlayer = { gameId, playerId ->
                        navController.navigate(GamePlayerDestination(gameId, playerId))
                    },
                )
            }
            composable<GamePlayerDestination> {
                GamePlayerScreen(
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<GameEditDestination> {
                GameEditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
        }

        navigation<PlayersTabDestination>(startDestination = PlayerGroupsDestination) {
            composable<PlayerGroupsDestination> {
                GroupsScreen<PlayerGroupsViewModel, Player, PlayerGroup>(
                    titleResId = R.string.player_groups_title,
                    navigateToGroupEntry = {
                        navController.navigate(route = PlayerGroupEntryDestination)
                    },
                    navigateToGroup = {
                        navController.navigate(route = PlayerGroupDestination(id = it))
                    },
                )
            }
            composable<PlayerGroupEntryDestination> {
                GroupEntryScreen<PlayerGroupEntryViewModel, Player, PlayerGroup>(
                    titleResId = R.string.player_group_entry_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<PlayerGroupDestination> {
                GroupScreen<PlayerGroupViewModel, Player, PlayerGroup>(
                    editContentDescriptionResId = R.string.player_group_edit_title,
                    deleteContentDescriptionResId = R.string.player_group_delete,
                    componentEntryContentDescriptionResId = R.string.player_entry_title,
                    deleteQuestionResId = R.string.group_delete_question,
                    navigateBack = { navController.navigateUp() },
                    navigateToGroupEdit = {
                        navController.navigate(route = PlayerGroupEditDestination(id = it))
                    },
                    navigateToComponentEdit = {
                        navController.navigate(route = PlayerEditDestination(id = it))
                    },
                    navigateToComponentEntry = {
                        navController.navigate(route = PlayerEntryDestination(groupId = it))
                    },
                )
            }
            composable<PlayerEntryDestination> {
                ComponentEntryScreen<PlayerEntryViewModel, Player, PlayerGroup, PlayerDetails>(
                    titleResId = R.string.player_entry_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<PlayerEditDestination> {
                ComponentEditScreen<PlayerEditViewModel, Player, PlayerGroup, PlayerDetails>(
                    titleResId = R.string.player_edit_title,
                    deleteContentDescriptionResId = R.string.player_delete,
                    deleteQuestionResId = R.string.player_delete_question,
                    deleteFailedMsgResId = R.string.player_delete_failed,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<PlayerGroupEditDestination> {
                GroupEditScreen<PlayerGroupEditViewModel, Player, PlayerGroup>(
                    titleResId = R.string.player_group_edit_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
        }

        navigation<PlacesTabDestination>(startDestination = PlaceGroupsDestination) {
            composable<PlaceGroupsDestination> {
                GroupsScreen<PlaceGroupsViewModel, Place, PlaceGroup>(
                    titleResId = R.string.place_groups_title,
                    navigateToGroupEntry = {
                        navController.navigate(route = PlaceGroupEntryDestination)
                    },
                    navigateToGroup = {
                        navController.navigate(route = PlaceGroupDestination(id = it))
                    },
                )
            }
            composable<PlaceGroupEntryDestination> {
                GroupEntryScreen<PlaceGroupEntryViewModel, Place, PlaceGroup>(
                    titleResId = R.string.place_group_entry_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<PlaceGroupDestination> {
                GroupScreen<PlaceGroupViewModel, Place, PlaceGroup>(
                    editContentDescriptionResId = R.string.place_group_edit_title,
                    deleteContentDescriptionResId = R.string.place_group_delete,
                    componentEntryContentDescriptionResId = R.string.place_entry_title,
                    deleteQuestionResId = R.string.group_delete_question,
                    navigateBack = { navController.navigateUp() },
                    navigateToGroupEdit = {
                        navController.navigate(route = PlaceGroupEditDestination(id = it))
                    },
                    navigateToComponentEdit = {
                        navController.navigate(route = PlaceEditDestination(id = it))
                    },
                    navigateToComponentEntry = {
                        navController.navigate(route = PlaceEntryDestination(groupId = it))
                    },
                )
            }
            composable<PlaceEntryDestination> {
                ComponentEntryScreen<PlaceEntryViewModel, Place, PlaceGroup, PlaceDetails>(
                    titleResId = R.string.place_entry_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<PlaceEditDestination> {
                ComponentEditScreen<PlaceEditViewModel, Place, PlaceGroup, PlaceDetails>(
                    titleResId = R.string.place_edit_title,
                    deleteContentDescriptionResId = R.string.place_delete,
                    deleteQuestionResId = R.string.place_delete_question,
                    deleteFailedMsgResId = R.string.place_delete_failed,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<PlaceGroupEditDestination> {
                GroupEditScreen<PlaceGroupEditViewModel, Place, PlaceGroup>(
                    titleResId = R.string.place_group_edit_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
        }

        navigation<WeaponsTabDestination>(startDestination = WeaponGroupsDestination) {
            composable<WeaponGroupsDestination> {
                GroupsScreen<WeaponGroupsViewModel, Weapon, WeaponGroup>(
                    titleResId = R.string.weapon_groups_title,
                    navigateToGroupEntry = {
                        navController.navigate(route = WeaponGroupEntryDestination)
                    },
                    navigateToGroup = {
                        navController.navigate(route = WeaponGroupDestination(id = it))
                    },
                )
            }
            composable<WeaponGroupEntryDestination> {
                GroupEntryScreen<WeaponGroupEntryViewModel, Weapon, WeaponGroup>(
                    titleResId = R.string.weapon_group_entry_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<WeaponGroupDestination> {
                GroupScreen<WeaponGroupViewModel, Weapon, WeaponGroup>(
                    editContentDescriptionResId = R.string.weapon_group_edit_title,
                    deleteContentDescriptionResId = R.string.weapon_group_delete,
                    componentEntryContentDescriptionResId = R.string.weapon_entry_title,
                    deleteQuestionResId = R.string.group_delete_question,
                    navigateBack = { navController.navigateUp() },
                    navigateToGroupEdit = {
                        navController.navigate(route = WeaponGroupEditDestination(id = it))
                    },
                    navigateToComponentEdit = {
                        navController.navigate(route = WeaponEditDestination(id = it))
                    },
                    navigateToComponentEntry = {
                        navController.navigate(route = WeaponEntryDestination(groupId = it))
                    },
                )
            }
            composable<WeaponEntryDestination> {
                ComponentEntryScreen<WeaponEntryViewModel, Weapon, WeaponGroup, WeaponDetails>(
                    titleResId = R.string.weapon_entry_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<WeaponEditDestination> {
                ComponentEditScreen<WeaponEditViewModel, Weapon, WeaponGroup, WeaponDetails>(
                    titleResId = R.string.weapon_edit_title,
                    deleteContentDescriptionResId = R.string.weapon_delete,
                    deleteQuestionResId = R.string.weapon_delete_question,
                    deleteFailedMsgResId = R.string.weapon_delete_failed,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
            composable<WeaponGroupEditDestination> {
                GroupEditScreen<WeaponGroupEditViewModel, Weapon, WeaponGroup>(
                    titleResId = R.string.weapon_group_edit_title,
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                )
            }
        }
    }
}
