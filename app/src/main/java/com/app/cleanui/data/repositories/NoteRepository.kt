package com.app.cleanui.data.repositories

import com.app.cleanui.data.entities.NoteEntity
import com.app.cleanui.data.mappers.NoteEntityMapper
import com.app.cleanui.domain.entities.NoteModel
import com.app.cleanui.domain.repositories.INoteRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val realm: Realm) : INoteRepository {

    @Inject
    lateinit var mapper: NoteEntityMapper

    override fun getAll(): Flow<List<NoteModel>> = flow {
        emit(realm.query<NoteEntity>().find().map { mapper.toModel(it) })
    }

    override fun getById(id: String): Flow<NoteModel?> = flow {
        realm.query<NoteEntity>("id == $id").first().find()?.let {
            emit(mapper.toModel(it))
            return@flow
        }
        emit(null)
    }

    override fun insert(model: NoteModel): Flow<NoteModel?> = flow {
        emit(mapper.toModel(realm.writeBlocking {
            copyToRealm(mapper.toEntity(model))
        }))
    }

    override fun update(model: NoteModel): Flow<Boolean> = flow {
        realm.writeBlocking {
            findLatest(mapper.toEntity(model))?.note = model.note
        }
        emit(true)
    }

    override fun delete(model: NoteModel): Flow<Boolean> = flow {
        realm.writeBlocking {
            delete(mapper.toEntity(model))
        }
        emit(true)
    }

}
