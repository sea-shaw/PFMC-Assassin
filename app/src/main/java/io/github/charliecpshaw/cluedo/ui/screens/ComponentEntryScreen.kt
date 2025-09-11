package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.ComponentDetails
import io.github.charliecpshaw.cluedo.ui.viewmodels.ComponentEntryUiState
import io.github.charliecpshaw.cluedo.ui.viewmodels.ComponentEntryViewModel
import io.github.charliecpshaw.cluedo.ui.viewmodels.PlayerDetails
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified V : ComponentEntryViewModel<C, G, D>, C : Component, G : Group, D : ComponentDetails<C, D>> ComponentEntryScreen(
    @StringRes titleResId: Int,
    crossinline navigateBack: () -> Unit,
    noinline onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: V = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    var canClickSave by rememberSaveable { mutableStateOf(true) }
    Scaffold(
        topBar = {
            CluedoTopAppBar(
                title = stringResource(titleResId),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
            )
        }
    ) { innerPadding ->
        ComponentEntryBody(
            uiState = viewModel.uiState,
            onValueChange = viewModel::updateUiState,
            canClickSave = canClickSave,
            onSaveClick = {
                coroutineScope.launch {
                    canClickSave = false
                    viewModel.saveComponent()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun <C : Component, D : ComponentDetails<C, D>> ComponentEntryBody(
    uiState: ComponentEntryUiState<C, D>,
    onValueChange: (D) -> Unit,
    canClickSave: Boolean,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
    ) {
        ComponentEntryForm(
            details = uiState.details,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onSaveClick,
            enabled = uiState.isValidInput && canClickSave,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

@Composable
private fun <C : Component, D : ComponentDetails<C, D>> ComponentEntryForm(
    details: D,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onValueChange: (D) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
    ) {
        OutlinedTextField(
            value = details.name,
            onValueChange = { onValueChange(details.copyName(it)) },
            label = { Text(stringResource(R.string.name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.is_active_req),
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = details.isActive,
                onCheckedChange = { onValueChange(details.copyIsActive(it)) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentEntryScreenPreview() {
    CluedoTheme {
        ComponentEntryBody(
            uiState = ComponentEntryUiState(
                details = PlayerDetails(name = "Player 0", emailAddress = "", isActive = true),
                isValidInput = true,
            ),
            onValueChange = {},
            canClickSave = true,
            onSaveClick = {},
        )
    }
}
