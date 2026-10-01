package com.example.rickandmorty.repository

import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharacterApi
import com.example.rickandmorty.model.CharacterDao
import com.example.rickandmorty.model.toCharacter
import com.example.rickandmorty.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(

    private val api: CharacterApi,

    private val dao: CharacterDao

) {

    val characters: Flow<List<Character>> =

        dao.observeCharacters()
            .map { entities ->

                entities.map { entity ->

                    entity.toCharacter()
                }
            }

    suspend fun syncCharacters() {

        val response = api.getCharacters()

        val characters = response.results.map { dto ->

            dto.toEntity()
        }

        dao.upsertCharacters(
            characters
        )
    }
}
