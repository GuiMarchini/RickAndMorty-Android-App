package com.example.rickandmorty.model

data class CharacterDto(

    val id: Int,

    val name: String,

    val species: String,

    val image: String
)

fun CharacterDto.toEntity(): CharacterEntity {

    return CharacterEntity(

        id = id,

        name = name,

        species = species,

        imageUrl = image
    )
}
