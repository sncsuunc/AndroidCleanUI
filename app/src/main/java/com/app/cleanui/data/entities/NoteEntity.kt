package com.app.cleanui.data.entities

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class NoteEntity: RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var note: String = ""
    var timestamp: Long = 0
}