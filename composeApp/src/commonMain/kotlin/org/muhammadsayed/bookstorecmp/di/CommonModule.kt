package org.muhammadsayed.bookstorecmp.di

import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.muhammadsayed.bookstorecmp.data.data_source.local.dao.BookDao
import org.muhammadsayed.bookstorecmp.data.data_source.local.dao.BookDaoImpl
import org.muhammadsayed.bookstorecmp.data.data_source.remote.HttpClientFactory
import org.muhammadsayed.bookstorecmp.data.data_source.settings.SettingsRepositoryImpl
import org.muhammadsayed.bookstorecmp.data.repository.AppRepositoryImpl
import org.muhammadsayed.bookstorecmp.domain.repository.AppRepository
import org.muhammadsayed.bookstorecmp.domain.repository.SettingsRepository
import org.muhammadsayed.bookstorecmp.domain.use_case.AddBook
import org.muhammadsayed.bookstorecmp.domain.use_case.DeleteBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetAlreadyRead
import org.muhammadsayed.bookstorecmp.domain.use_case.GetBookDetails
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCartItems
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCurrentlyReading
import org.muhammadsayed.bookstorecmp.presentation.cart.CartViewModel
import org.muhammadsayed.bookstorecmp.presentation.details.DetailsViewModel
import org.muhammadsayed.bookstorecmp.presentation.home.HomeViewModel
import org.muhammadsayed.bookstorecmp.presentation.settings.SettingsViewModel

@OptIn(ExperimentalSettingsApi::class)
fun commonModule(enableNetworkLogs: Boolean) = module {
    /**
     * Creates a http client for Ktor that is provided to the
     * API client via constructor injection
     */
    single {
        HttpClientFactory.makeClient(enableNetworkLogs = enableNetworkLogs)
    }

    single<BookDao> { BookDaoImpl(databaseDriverFactory = get()) }
    single<AppRepository> { AppRepositoryImpl(httpClient = get(), dao = get()) }
    factory<GetCurrentlyReading> { GetCurrentlyReading(get()) }
    factory<GetAlreadyRead> { GetAlreadyRead(get()) }
    factory<GetBookDetails> { GetBookDetails(get()) }
    factory<GetCartItems> { GetCartItems(get()) }
    factory<AddBook> { AddBook(get()) }
    factory<DeleteBook> { DeleteBook(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(observableSettings = get()) }

    singleOf(::HomeViewModel)
    singleOf(::DetailsViewModel)
    singleOf(::CartViewModel)
    singleOf(::SettingsViewModel)

}

expect fun platformModule(): Module
