package com.example.rickandmorty.model

import retrofit2.http.GET

interface CharacterApi {

    @GET("character")
    suspend fun getCharacters(): CharacterApiResponse
}
