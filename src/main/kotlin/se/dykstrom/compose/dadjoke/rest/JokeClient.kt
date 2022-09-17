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

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import se.dykstrom.compose.dadjoke.model.Joke
import kotlin.random.Random

class JokeClient {

    suspend fun getRandomJoke(): Joke {
        val service = createJokeService()
        return service.getJoke()
    }

    suspend fun getRandomJokeBySearchTerm(searchTerm: String): Joke {
        val service = createJokeService()
        val listWithFirstJoke = service.search(searchTerm, 1)
        val totalNumberOfJokes = listWithFirstJoke.totalJokes
        if (totalNumberOfJokes == 0) {
            throw NoSuchElementException("No jokes about '$searchTerm' found.")
        }
        val index = Random.Default.nextInt(totalNumberOfJokes) + 1
        val listWithRandomJoke = service.search(searchTerm, index)
        return listWithRandomJoke.results[0]
    }

    private fun createJokeService() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(JokeService::class.java)

    companion object {
        private const val BASE_URL = "https://icanhazdadjoke.com/"
    }
}
