package org.muhammadsayed.bookstorecmp.data.data_source.remote

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun makeClient(
        httpClientEngine: HttpClientEngine? = null,
        enableNetworkLogs: Boolean
    ): HttpClient {

      return  HttpClient(httpClientEngine!!) {
            install(DefaultRequest) {
                url("https://openlibrary.org")
                headers {
                    appendIfNameAbsent(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                }
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
            }

            install(ContentNegotiation) {
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
                json(json = json)
                register(ContentType.Text.Plain, KotlinxSerializationConverter(json))
            }

          if (enableNetworkLogs) {
              install(Logging) {
                  level = LogLevel.ALL
                  logger = object : Logger {
                      override fun log(message: String) {
                          Napier.i(tag = "Http Client", message = message)
                      }
                  }
              }.also {
                  Napier.base(DebugAntilog())
              }
          }
        }
       /* return   HttpClient(httpClientEngine!!)  {
            expectSuccess = true
            addDefaultResponseValidation()

            defaultRequest {
                url("https://openlibrary.org")
                headers {
                    appendIfNameAbsent(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                }
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
            }

            if (enableNetworkLogs) {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.i(tag = "Http Client", message = message)
                        }
                    }
                }.also {
                    Napier.base(DebugAntilog())
                }
            }

            install(ContentNegotiation) {
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
                json(json = json)
                register(ContentType.Text.Plain, KotlinxSerializationConverter(json))
            }

        }*/
    }
}
