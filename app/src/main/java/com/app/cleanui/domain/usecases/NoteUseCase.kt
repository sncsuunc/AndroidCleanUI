package com.app.cleanui.domain.usecases

import com.app.cleanui.domain.entities.NoteModel
import com.app.cleanui.domain.repositories.INoteRepository
import com.app.cleanui.domain.repositories.NoteRPS
import javax.inject.Inject

class NoteUseCase @Inject constructor(@NoteRPS private val repository: INoteRepository) {

    fun addNote(model: NoteModel) = repository.insert(model)

    fun getNotes() = repository.getAll()

}