package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.realm.RealmDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class PetsViewModel : ViewModel() {
    private val database = RealmDatabase()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val pets = database.getAllPets().mapToStateFlow(
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmPets ->
        realmPets.map { realmPet ->
            Pet(
                id = realmPet.id.toHexString(),
                name = realmPet.name,
                age = realmPet.age,
                type = realmPet.type,
                owner = realmPet.owner?.let { realmOwner ->
                    Owner(
                        id = realmOwner.id.toHexString(),
                        name = realmOwner.name,
                        pets = emptyList(),
                    )
                },
            )
        }
    }

    private val _addPetDialogState = MutableStateFlow<AddPetDialogState>(AddPetDialogState.Hidden)
    val addPetDialogState = _addPetDialogState.asStateFlow()

    private val _removePetDialogState =
        MutableStateFlow<RemovePetDialogState>(RemovePetDialogState.Hidden)
    val removePetDialogState = _removePetDialogState.asStateFlow()


    fun updateSearchQuery(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    fun showPetDialog() {
        _addPetDialogState.update { state ->
            if (state == AddPetDialogState.Hidden) AddPetDialogState.Visible() else state
        }
    }

    fun hidePetDialog() {
        _addPetDialogState.update { AddPetDialogState.Hidden }
    }

    fun updatePetName(newPetName: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    petName = newPetName,
                    hasPetNameWarning = false,
                )
            } else it
        }
    }

    fun updatePetAge(newPetAge: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                val petAge = when (newPetAge) {
                    "" -> null
                    else -> newPetAge.toIntOrNull() ?: it.petAge
                }
                it.copy(
                    petAge = petAge,
                    hasPetAgeWarning = false,
                )
            } else it
        }
    }

    fun updatePetType(newPetType: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    petType = newPetType,
                    hasPetTypeWarning = false,
                )
            } else it
        }
    }

    fun updateHasOwner(newHasOwner: Boolean) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    hasOwner = newHasOwner,
                    ownerName = if (!newHasOwner) "" else it.ownerName,
                )
            } else it
        }
    }

    fun updateOwnerName(newOwnerName: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    ownerName = newOwnerName,
                    hasOwnerNameWarning = false,
                )
            } else it
        }
    }

    fun addPet() {
        var state = addPetDialogState.value
        with(state) {
            if (this is AddPetDialogState.Visible) {
                if (petName.isBlank()) state = copy(hasPetNameWarning = true)
                if (petAge == null) state = copy(hasPetAgeWarning = true)
                if (petType.isBlank()) state = copy(hasPetTypeWarning = true)
                if (hasOwner && ownerName.isBlank()) state = copy(hasOwnerNameWarning = true)

                if (!hasWarning) {
                    viewModelScope.launch {
                        database.addPet(
                            name = petName,
                            age = petAge!!,
                            type = petType,
                            ownerName = ownerName,
                        )
                    }
                    state = AddPetDialogState.Hidden
                }
            }
        }
        _addPetDialogState.update { state }
    }

    fun initiateRemovePet(pet: Pet) {
        _removePetDialogState.update { RemovePetDialogState.Visible(pet) }
    }

    fun cancelRemovePet() {
        _removePetDialogState.update { RemovePetDialogState.Hidden }
    }

    fun removePet() {
        val state = removePetDialogState.value
        if (state is RemovePetDialogState.Visible) {
            viewModelScope.launch {
                database.deletePet(id = ObjectId(state.pet.id))
            }
        }
    }
}