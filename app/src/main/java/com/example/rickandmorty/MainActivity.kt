package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickandmorty.view.CharacterScreen
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.example.rickandmorty.viewmodel.CharacterViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application =
            application as RickAndMortyApplication

        val factory = CharacterViewModelFactory(
            repository = application.repository
        )

        setContent {

            val viewModel: CharacterViewModel = viewModel(
                factory = factory
            )

            MaterialTheme {
                Surface {
                    CharacterScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
