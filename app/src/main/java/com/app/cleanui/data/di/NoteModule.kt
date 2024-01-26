package com.app.cleanui.data.di

import com.app.cleanui.data.repositories.NoteRepository
import com.app.cleanui.domain.repositories.INoteRepository
import com.app.cleanui.domain.repositories.NoteRPS
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteModule {

    @Binds
    @NoteRPS
    @Singleton
    abstract fun bindNoteRepository(impl: NoteRepository): INoteRepository

}