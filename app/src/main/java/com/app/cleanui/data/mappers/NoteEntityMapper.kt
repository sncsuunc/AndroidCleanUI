package com.app.cleanui.data.mappers

import com.app.cleanui.data.entities.NoteEntity
import com.app.cleanui.domain.entities.NoteModel
import javax.inject.Inject

class NoteEntityMapper @Inject constructor() {

    fun toModel(entity: NoteEntity): NoteModel {
        return NoteModel().apply {
            id = entity.id
            note = entity.note
            timestamp = entity.timestamp
        }
    }

    fun toEntity(model: NoteModel): NoteEntity {
        return NoteEntity().apply {
            id = model.id
            timestamp = model.timestamp
            note = model.note
        }
    }

}