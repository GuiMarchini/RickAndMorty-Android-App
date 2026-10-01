package com.example.rickandmorty.model

data class CharacterApiResponse(

    val info: ApiInfo,

    val results: List<CharacterDto>
)

data class ApiInfo(

    val count: Int,

    val pages: Int,

    val next: String?,

    val prev: String?
)
