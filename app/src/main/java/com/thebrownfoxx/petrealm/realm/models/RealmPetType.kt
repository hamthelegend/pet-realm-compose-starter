package com.thebrownfoxx.petrealm.realm.models

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class RealmPetType : RealmObject {
    var id: ObjectId = ObjectId()
    var name: String = ""
}