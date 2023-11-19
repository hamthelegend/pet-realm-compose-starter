package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.application

@Destination
@Composable
fun Pets(navigator: DestinationsNavigator) {
    val viewModel = viewModel { PetsViewModel(application.database) }

    with(viewModel) {
        val pets by pets.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val addPetDialogState by addPetDialogState.collectAsStateWithLifecycle()
        val removePetDialogState by removePetDialogState.collectAsStateWithLifecycle()

        PetsScreen(
            pets = pets,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            addPetDialogState = addPetDialogState,
            addPetDialogStateChangeListener = AddPetDialogStateChangeListener(
                onShowAddPetDialog = ::showPetDialog,
                onHideAddPetDialog = ::hidePetDialog,
                onPetNameChange = ::updatePetName,
                onPetAgeChange = ::updatePetAge,
                onPetTypeChange = ::updatePetType,
                onHasOwnerChange = ::updateHasOwner,
                onOwnerNameChange = ::updateOwnerName,
                onAddPet = ::addPet
            ),
            removePetDialogState = removePetDialogState,
            removePetDialogStateChangeListener = RemovePetDialogStateChangeListener(
                onInitiateRemovePet = ::initiateRemovePet,
                onCancelRemovePet = ::cancelRemovePet,
                onRemovePet = ::removePet
            )
        )
    }
}