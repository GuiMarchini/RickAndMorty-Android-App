package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.repository.CharacterRepository

class CharacterViewModelFactory(

    private val repository: CharacterRepository

) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                CharacterViewModel::class.java
            )
        ) {

            return CharacterViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconhecida"
        )
    }
}
