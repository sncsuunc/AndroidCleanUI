package com.app.cleanui.data.services

import com.app.cleanui.domain.entities.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteApi {
    fun getNotes(): Flow<List<NoteModel>>
}