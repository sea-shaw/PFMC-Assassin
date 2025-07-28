package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme

@Composable
fun DeleteConfirmationDialogue(
    @StringRes deleteQuestionResId: Int,
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDeleteCancel) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = deleteQuestionResId),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = onDeleteCancel,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = onDeleteConfirm,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteConfirmationDialoguePreview() {
    CluedoTheme {
        DeleteConfirmationDialogue(
            deleteQuestionResId = R.string.group_delete_question,
            onDeleteConfirm = {},
            onDeleteCancel = {},
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}
