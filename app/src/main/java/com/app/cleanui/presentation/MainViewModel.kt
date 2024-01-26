package com.app.cleanui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cleanui.domain.entities.NoteModel
import com.app.cleanui.domain.usecases.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
) : ViewModel() {

    private val _permission: MutableStateFlow<Unit> = MutableStateFlow(Unit)
    val permission: Flow<Unit> = _permission

    val notes: Flow<List<NoteModel>> = noteUseCase.getNotes()

    fun checkNotificationPermission(){
        viewModelScope.launch {
            delay(1500L)
            _permission.emit(Unit)
        }
    }

    fun addNote(noteModel: NoteModel) {
        viewModelScope.launch {
            noteUseCase.addNote(noteModel).collect {

            }
        }
    }

}