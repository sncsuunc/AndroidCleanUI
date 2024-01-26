package com.app.cleanui.domain.repositories

import com.app.cleanui.domain.entities.NoteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Qualifier

@Qualifier
annotation class NoteRPS

interface INoteRepository {

    fun getAll(): Flow<List<NoteModel>>

    fun getById(id: String): Flow<NoteModel?>

    fun insert(model: NoteModel): Flow<NoteModel?>

    fun update(model: NoteModel): Flow<Boolean>

    fun delete(model: NoteModel): Flow<Boolean>

}