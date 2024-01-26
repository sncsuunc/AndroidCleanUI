package com.app.cleanui.domain.entities

import java.util.UUID

class NoteModel {
    var id: String = UUID.randomUUID().toString()
    var note: String = ""
    var timestamp: Long = 0
}