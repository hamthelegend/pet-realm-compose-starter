package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun AddPetDialog(
    state: AddPetDialogState,
    stateChangeListener: AddPetDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if (state is AddPetDialogState.Visible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onHideAddPetDialog,
            title = { Text(text = "Add Pet") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    TextField(
                        label = { Text(text = "Name") },
                        value = state.petName,
                        onValueChange = stateChangeListener.onPetNameChange,
                        isError = state.hasPetNameWarning,
                    )
                    TextField(
                        label = { Text(text = "Age") },
                        value = state.petAge?.toString() ?: "",
                        onValueChange = stateChangeListener.onPetAgeChange,
                        isError = state.hasPetAgeWarning,
                    )
                    TextField(
                        label = { Text(text = "Type") },
                        value = state.petType,
                        onValueChange = stateChangeListener.onPetTypeChange,
                        isError = state.hasPetTypeWarning,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = state.hasOwner,
                            onCheckedChange = stateChangeListener.onHasOwnerChange,
                        )
                        Text(text = "Has owner?")
                    }
                    TextField(
                        label = { Text(text = "Owner name") },
                        value = state.ownerName,
                        onValueChange = stateChangeListener.onOwnerNameChange,
                        enabled = state.hasOwner,
                        isError = state.hasOwnerNameWarning,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onHideAddPetDialog,
                )
            },
            confirmButton = { FilledButton(text = "Add", onClick = stateChangeListener.onAddPet) }
        )
    }
}