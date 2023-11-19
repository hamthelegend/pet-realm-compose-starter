package com.thebrownfoxx.petrealm.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import com.thebrownfoxx.petrealm.realm.models.RealmOwner
import com.thebrownfoxx.petrealm.realm.models.RealmPet
import com.thebrownfoxx.petrealm.realm.models.RealmPetType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class PetRealmDatabase {
    private val config = RealmConfiguration.Builder(
        schema = setOf(RealmPet::class, RealmOwner::class, RealmPetType::class)
    ).schemaVersion(1)
        .initialData {
            copyToRealm(RealmPetType().apply { name = "Cat" })
            copyToRealm(RealmPetType().apply { name = "Dog" })
        }
        .build()
    private val realm: Realm = Realm.open(config)

    fun getAllPetTypes(): Flow<List<RealmPetType>> =
        realm.query<RealmPetType>().asFlow().map { it.list }

    fun getAllPets(): Flow<List<RealmPet>> = realm.query<RealmPet>().asFlow().map { it.list }

//    fun getPetsByName(name: String): List<PetRealm> {
//        return realm.query<PetRealm>("name CONTAINS $0", name).find()
//    }

//    suspend fun addPet(
//        name: String,
//        age: Int,
//        type: RealmPetType,
//        ownerName: String = "",
//    ) {
//        realm.write {
//            val pet = RealmPet().apply {
//                this.name = name
//                this.age = age
//                this.type = type
//            }
//            val managedPet = copyToRealm(pet)
//            if (ownerName.isNotEmpty()) {
//                val ownerResult: RealmOwner? =
//                    realm.query<RealmOwner>("name == $0", ownerName).first().find()
//                if (ownerResult == null) {
//                    val owner = RealmOwner().apply {
//                        this.name = ownerName
//                        this.pets.add(managedPet)
//                    }
//                    val managedOwner = copyToRealm(owner)
//                    managedPet.owner = managedOwner
//                } else {
//                    findLatest(ownerResult)?.pets?.add(managedPet)
//                    findLatest(managedPet)?.owner = findLatest(ownerResult)
//                }
//            }
//        }
//    }

    suspend fun addPet(
        name: String,
        age: Int,
        type: String = "",
        ownerName: String = "",
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val pet = RealmPet().apply {
                    this.name = name
                    this.age = age
                    this.type = type
                }
                val managedPet = copyToRealm(pet)
                if (ownerName.isNotEmpty()) {
                    val ownerResult: RealmOwner? =
                        realm.query<RealmOwner>("name == $0", ownerName).first().find()
                    if (ownerResult == null) {
                        val owner = RealmOwner().apply {
                            this.name = ownerName
                            this.pets.add(managedPet)
                        }
                        val managedOwner = copyToRealm(owner)
                        managedPet.owner = managedOwner
                    } else {
                        findLatest(ownerResult)?.pets?.add(managedPet)
                        findLatest(managedPet)?.owner = findLatest(ownerResult)
                    }
                }
            }
        }
    }

    suspend fun deletePet(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                query<RealmPet>("id == $0", id)
                    .first()
                    .find()
                    ?.let { delete(it) }
                    ?: throw IllegalStateException("Pet not found!")
            }
        }
    }

    fun getAllOwners(): Flow<List<RealmOwner>> = realm.query<RealmOwner>().asFlow().map { it.list }
}