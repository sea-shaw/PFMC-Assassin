package io.github.charliecpshaw.cluedo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.ui.viewmodels.NameEntryUiState

@Composable
fun NameEntryBody(
  uiState: NameEntryUiState,
  onNameValueChange: (String) -> Unit,
  @StringRes saveActionResId: Int,
  canClickSave: Boolean,
  onSaveClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
  ) {
    GroupEntryForm(
      name = uiState.name,
      onValueChange = onNameValueChange,
      modifier = Modifier.fillMaxWidth(),
    )
    Button(
      onClick = onSaveClick,
      enabled = uiState.isValidInput && canClickSave,
      shape = MaterialTheme.shapes.small,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(text = stringResource(saveActionResId))
    }
  }
}

@Composable
private fun GroupEntryForm(
  name: String,
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
  ) {
    OutlinedTextField(
      value = name,
      onValueChange = { onValueChange(it) },
      label = { Text(stringResource(R.string.name_req)) },
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
      ),
      modifier = Modifier.fillMaxWidth(),
      singleLine = true,
    )
  }
}
