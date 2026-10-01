package com.example.rickandmorty

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.model.CharacterApi
import com.example.rickandmorty.model.CharacterDatabase
import com.example.rickandmorty.repository.CharacterRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyApplication : Application() {

    lateinit var repository: CharacterRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val database = Room.databaseBuilder(
            applicationContext,
            CharacterDatabase::class.java,
            "rickandmorty_database"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val api = retrofit.create(
            CharacterApi::class.java
        )

        repository = CharacterRepository(
            api = api,
            dao = database.characterDao()
        )
    }
}
