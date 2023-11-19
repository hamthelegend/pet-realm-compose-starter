package com.thebrownfoxx.petrealm

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase

class PetRealmApplication: Application() {
    lateinit var database: PetRealmDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = PetRealmDatabase()
    }
}

val CreationExtras.application
    get() = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PetRealmApplication)