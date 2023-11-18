package com.thebrownfoxx.petrealm.realm.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmOwner : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var pets: RealmList<RealmPet> = realmListOf()
}