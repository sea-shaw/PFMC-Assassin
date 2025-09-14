package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme

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
