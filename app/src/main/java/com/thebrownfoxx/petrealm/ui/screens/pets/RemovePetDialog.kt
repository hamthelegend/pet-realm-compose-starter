package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun RemovePetDialog(
    state: RemovePetDialogState,
    stateChangeListener: RemovePetDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if (state is RemovePetDialogState.Visible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onCancelRemovePet,
            title = { Text(text = "Remove Pet") },
            text = { Text(text = "Are you sure you want to remove ${state.pet.name}?") },
            dismissButton = {
                TextButton(text = "No", onClick = stateChangeListener.onRemovePet)
            },
            confirmButton = {
                FilledButton(
                    text = "Yes",
                    onClick = stateChangeListener.onRemovePet,
                )
            },
        )
    }
}