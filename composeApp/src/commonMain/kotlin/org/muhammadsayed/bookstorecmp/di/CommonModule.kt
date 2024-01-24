package org.muhammadsayed.bookstorecmp.di

import com.russhwolf.settings.ExperimentalSettingsApi
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.muhammadsayed.bookstorecmp.data.data_source.local.dao.BookDao
import org.muhammadsayed.bookstorecmp.data.repository.AppRepositoryImpl
import org.muhammadsayed.bookstorecmp.domain.repository.AppRepository
import org.muhammadsayed.bookstorecmp.domain.use_case.AddBook
import org.muhammadsayed.bookstorecmp.domain.use_case.DeleteBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetAlreadyRead
import org.muhammadsayed.bookstorecmp.domain.use_case.GetBookDetails
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCartItems
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCurrentlyReading
import org.muhammadsayed.bookstorecmp.presentation.cart.CartViewModel
import org.muhammadsayed.bookstorecmp.presentation.details.DetailsViewModel
import org.muhammadsayed.bookstorecmp.presentation.home.HomeViewModel

@OptIn(ExperimentalSettingsApi::class)
fun commonModule(enableNetworkLogs: Boolean) = module {
    /**
     * Creates a http client for Ktor that is provided to the
     * API client via constructor injection
     */
    single {
        HttpClient {
            expectSuccess = true
            addDefaultResponseValidation()

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "openlibrary.org"
                }
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
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
        }
    }

    single { BookDao(databaseDriverFactory = get()) }
    single<AppRepository> { AppRepositoryImpl(httpClient = get(), dao = get()) }
    factory<GetCurrentlyReading> { GetCurrentlyReading(get()) }
    factory<GetAlreadyRead> { GetAlreadyRead(get()) }
    factory<GetBookDetails> { GetBookDetails(get()) }
    factory<GetCartItems> { GetCartItems(get()) }
    factory<AddBook> { AddBook(get()) }
    factory<DeleteBook> { DeleteBook(get()) }
    singleOf(::HomeViewModel)
    singleOf(::DetailsViewModel)
    singleOf(::CartViewModel)
}

expect fun platformModule(): Module
