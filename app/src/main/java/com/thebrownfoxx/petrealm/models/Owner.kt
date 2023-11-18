package com.thebrownfoxx.petrealm.models

data class Owner(
    val id: String = "",
    val name: String,
    val pets: List<Pet> = emptyList(),
)