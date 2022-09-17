/*
 * Copyright 2022 Johan Dykström
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.dykstrom.compose.dadjoke

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import se.dykstrom.compose.dadjoke.rest.JokeClient

private val jokeClient = JokeClient()

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "icanhazdadjoke.com",
        state = rememberWindowState(width = 400.dp, height = 300.dp)
    ) {
        var isSearching by remember { mutableStateOf(false) }

        MaterialTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                mainLayer { value -> isSearching = value }
                progressLayer { isSearching }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun mainLayer(isSearching: (Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        var searchTerm by remember { mutableStateOf("") }
        var searchResult by remember { mutableStateOf("") }

        val search: () -> Unit = {
            MainScope().launch {
                searchResult = try {
                    isSearching(true)
                    getJoke(searchTerm.trim()).joke
                } catch (e: Exception) {
                    "Failed to get joke: ${e.message}"
                } finally {
                    isSearching(false)
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier.weight(1.0f).onKeyEvent { onKeyEvent ->
                    if (onKeyEvent.key != Key.Enter) return@onKeyEvent false
                    if (onKeyEvent.type == KeyEventType.KeyUp) search()
                    true
                },
                value = searchTerm,
                onValueChange = { searchTerm = it },
                singleLine = true,
                label = { Text("Search for joke") }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = search
            ) {
                Text(text = "Get joke", maxLines = 1)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = searchResult,
                onValueChange = { },
                readOnly = true
            )
        }
    }
}

@Preview
@Composable
fun previewMainLayer() = mainLayer { }

@Composable
fun progressLayer(isSearching: () -> Boolean) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isSearching()) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

private suspend fun getJoke(searchTerm: String) =
    if (searchTerm.isBlank())
        jokeClient.getRandomJoke()
    else
        jokeClient.getRandomJokeBySearchTerm(searchTerm)
