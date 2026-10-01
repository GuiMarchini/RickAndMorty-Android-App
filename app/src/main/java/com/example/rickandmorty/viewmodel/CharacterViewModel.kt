package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters = repository.characters
        .stateIn(
            scope = viewModelScope,

            started = SharingStarted.WhileSubscribed(
                5_000
            ),

            initialValue = emptyList()
        )

    private val _syncState =
        MutableStateFlow<SyncState>(
            SyncState.Idle
        )

    val syncState: StateFlow<SyncState> =
        _syncState.asStateFlow()

    fun syncCharacters() {

        viewModelScope.launch {

            _syncState.value =
                SyncState.Loading

            try {

                repository.syncCharacters()

                _syncState.value =
                    SyncState.Success

            } catch (exception: Exception) {

                _syncState.value =
                    SyncState.Error(
                        message =
                            "Erro de conexão. " +
                                    "Verifique sua internet."
                    )
            }
        }
    }
}
