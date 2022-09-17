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

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import se.dykstrom.compose.dadjoke.model.Joke
import se.dykstrom.compose.dadjoke.model.JokeList

interface JokeService {

    @Headers(
        "Accept: application/json",
        "User-Agent: compose-dad-joke (https://github.com/dykstrom/compose-dad-joke)"
    )
    @GET("/")
    suspend fun getJoke(): Joke

    @Headers(
        "Accept: application/json",
        "User-Agent: compose-dad-joke (https://github.com/dykstrom/compose-dad-joke)"
    )
    @GET("/search")
    suspend fun search(
        @Query("term") term: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 1
    ): JokeList
}
