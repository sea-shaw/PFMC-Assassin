package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.charliecpshaw.cluedo.CluedoTopAppBar
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Component
import io.github.charliecpshaw.cluedo.data.model.Group
import io.github.charliecpshaw.cluedo.ui.viewmodels.AppViewModelProvider
import io.github.charliecpshaw.cluedo.ui.viewmodels.GroupEditViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified V : GroupEditViewModel<C, G>, C : Component, G : Group> GroupEditScreen(
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
        NameEntryBody(
            uiState = viewModel.uiState,
            onNameValueChange = viewModel::updateUiState,
            saveActionResId = R.string.save_action,
            canClickSave = canClickSave,
            onSaveClick = {
                coroutineScope.launch {
                    canClickSave = false
                    viewModel.saveGroup()
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
