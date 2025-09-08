package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.data.model.Player
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GroupViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified V : GroupViewModel<C, G>, C : Component, G : Group> GroupScreen(
    @StringRes editContentDescriptionResId: Int,
    @StringRes deleteContentDescriptionResId: Int,
    @StringRes componentEntryContentDescriptionResId: Int,
    @StringRes deleteQuestionResId: Int,
    crossinline navigateToGroupEdit: (Long) -> Unit,
    noinline navigateToComponentEdit: (Long) -> Unit,
    crossinline navigateToComponentEntry: (Long) -> Unit,
    noinline navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: V = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    var deleteConformationRequired by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CluedoTopAppBar(
                title = uiState.name,
                canNavigateBack = true,
                navigateUp = navigateBack,
                hasEditButton = true,
                onEditClick = { navigateToGroupEdit(viewModel.groupId) },
                editContentDescriptionRes = editContentDescriptionResId,
                hasDeleteButton = true,
                onDeleteClick = { deleteConformationRequired = true },
                deleteContentDescriptionResId = deleteContentDescriptionResId,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToComponentEntry(viewModel.groupId) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(componentEntryContentDescriptionResId),
                )
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        GroupBody(
            componentList = uiState.components,
            onComponentClick = navigateToComponentEdit,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
        if (deleteConformationRequired) {
            ConfirmationDialogue(
                dialogueText = stringResource(id = deleteQuestionResId),
                confirmTextResId = R.string.delete,
                cancelTextResId = R.string.cancel,
                onConfirm = {
                    deleteConformationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteGroup()
                        navigateBack()
                    }
                },
                onCancel = { deleteConformationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun <C : Component> GroupBody(
    componentList: List<C>,
    onComponentClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        ComponentList(
            componentList = componentList,
            contentPadding = contentPadding,
            onComponentClick = onComponentClick,
            modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
        )
    }
}

@Composable
private fun <C : Component> ComponentList(
    componentList: List<C>,
    contentPadding: PaddingValues,
    onComponentClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        if (componentList.isNotEmpty()) {
            items(
                items = componentList,
                key = { it.id },
            ) { player ->
                ComponentCard(
                    component = player,
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onComponentClick(player.id) },
                )
            }
        }
    }
}

@Composable
private fun <C : Component> ComponentCard(
    component: C,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = component.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (component.isActive) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.active),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.inactive),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerGroupBodyPreview() {
    CluedoTheme {
        GroupBody(
            componentList = listOf(
                Player(id = 0, name = "Player 0", isActive = true, groupId = 0),
                Player(id = 1, name = "Player 1", isActive = false, groupId = 0),
            ),
            onComponentClick = {},
        )
    }
}
