package com.thebrownfoxx.petrealm.ui.screens.owners

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.petrealm.models.Owner

@Composable
fun OwnerCard(
    owner: Owner,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Card(modifier = modifier) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(text = "Name: ${owner.name}")
                Text(
                    text = "Pets: ${owner.pets.joinToString(", ") { it.name }}",
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}