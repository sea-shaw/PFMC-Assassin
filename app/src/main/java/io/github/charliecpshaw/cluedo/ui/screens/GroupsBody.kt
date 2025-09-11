package io.github.charliecpshaw.cluedo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.Group

@Composable
fun <G : Group<*>> GroupsBody(
    groupList: List<G>,
    onGroupClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        GroupsList(
            groupList = groupList,
            onGroupClick = { onGroupClick(it.id) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun <G : Group<*>> GroupsList(
    groupList: List<G>,
    onGroupClick: (G) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        if (groupList.isNotEmpty()) {
            items(
                items = groupList,
                key = { it.id },
            ) { group ->
                GroupCard(
                    group = group,
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onGroupClick(group) }
                )
            }
        }
    }
}

@Composable
private fun <G : Group<*>> GroupCard(
    group: G,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}
