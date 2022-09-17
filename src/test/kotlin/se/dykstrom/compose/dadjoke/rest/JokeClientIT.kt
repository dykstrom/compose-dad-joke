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
package se.dykstrom.compose.dadjoke.rest

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class JokeClientIT {

    private val client = JokeClient()

    @Test
    fun shouldGetRandomJoke(): Unit = runBlocking {
        // When
        val joke = client.getRandomJoke().joke

        // Then
        assertTrue(joke.isNotBlank())
    }

    @Test
    fun shouldGetRandomJokeBySearchTerm(): Unit = runBlocking {
        // When
        val joke = client.getRandomJokeBySearchTerm("hipster").joke

        // Then
        assertTrue(joke.isNotBlank())
    }
}
