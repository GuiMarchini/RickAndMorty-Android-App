package com.example.rickandmorty.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.example.rickandmorty.viewmodel.SyncState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel
) {

    val characters by viewModel.characters
        .collectAsStateWithLifecycle()

    val syncState by viewModel.syncState
        .collectAsStateWithLifecycle()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Rick and Morty",
                        fontWeight = FontWeight.Bold
                    )
                },

                actions = {

                    IconButton(
                        onClick = {
                            viewModel.syncCharacters()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Refresh,

                            contentDescription =
                                "Sincronizar"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            SyncStatus(
                state = syncState
            )

            CharacterList(
                characters = characters
            )
        }
    }
}

@Composable
private fun SyncStatus(
    state: SyncState
) {

    when (state) {

        SyncState.Idle -> {
            // Nenhuma mensagem.
        }

        SyncState.Loading -> {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text =
                        "Sincronizando personagens..."
                )
            }
        }

        SyncState.Success -> {

            Text(
                text =
                    "✓ Sincronização concluída",

                color =
                    MaterialTheme.colorScheme.primary,

                modifier =
                    Modifier.padding(16.dp)
            )
        }

        is SyncState.Error -> {

            Text(
                text =
                    "⚠ ${state.message}",

                color =
                    MaterialTheme.colorScheme.error,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun CharacterList(
    characters: List<Character>
) {

    if (characters.isEmpty()) {

        EmptyCharacters()

        return
    }

    LazyColumn(

        modifier = Modifier.fillMaxSize(),

        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        ),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)

    ) {

        items(

            items = characters,

            key = { character ->
                character.id
            }

        ) { character ->

            CharacterCard(
                character = character
            )
        }
    }
}

@Composable
private fun EmptyCharacters() {

    Box(
        modifier = Modifier.fillMaxSize(),

        contentAlignment =
            Alignment.Center
    ) {

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "🛸",

                style =
                    MaterialTheme.typography
                        .displayMedium
            )

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            Text(
                text =
                    "Nenhum personagem salvo."
            )

            Text(
                text =
                    "Toque em atualizar para sincronizar."
            )
        }
    }
}
