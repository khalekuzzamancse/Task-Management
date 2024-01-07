package com.khalekuzzamanjustcse.taskmanagement.features.common_ui

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable


class Response(
  private val  url: String
) {

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }

    fun close() {
        httpClient.close()
    }


    private suspend inline fun <reified T> getG(): List<T> =
        httpClient
            .get(url)
            .body<List<T>>()
     suspend fun get(): List<BirdImage> = getG<BirdImage>()

}

@Serializable
data class BirdImage(
    val category: String,
    val path: String,
    val author: String
)

