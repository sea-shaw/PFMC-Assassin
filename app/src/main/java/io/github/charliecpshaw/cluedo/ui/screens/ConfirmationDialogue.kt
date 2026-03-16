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
fun ConfirmationDialogue(
  dialogueText: String,
  @StringRes confirmTextResId: Int,
  @StringRes cancelTextResId: Int,
  onConfirm: () -> Unit,
  onCancel: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Dialog(onDismissRequest = onCancel) {
    Card(
      modifier = modifier,
      shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
    ) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = dialogueText,
          modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End,
        ) {
          TextButton(
            onClick = onCancel,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
          ) {
            Text(text = stringResource(id = cancelTextResId))
          }
          TextButton(
            onClick = onConfirm,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
          ) {
            Text(text = stringResource(id = confirmTextResId))
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
    ConfirmationDialogue(
      dialogueText = stringResource(id = R.string.group_delete_question),
      confirmTextResId = R.string.delete,
      cancelTextResId = R.string.cancel,
      onConfirm = {},
      onCancel = {},
      modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    )
  }
}
