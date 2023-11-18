package com.thebrownfoxx.petrealm.ui.screens.owners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.petrealm.models.Owner

@Composable
fun OwnersScreen(
    owners: List<Owner>,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding + PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(owners) { owner ->
                OwnerCard(owner = owner)
            }
        }
    }
}