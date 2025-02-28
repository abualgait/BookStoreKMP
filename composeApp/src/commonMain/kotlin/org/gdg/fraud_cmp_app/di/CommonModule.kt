package org.gdg.fraud_cmp_app.di

import com.russhwolf.settings.ExperimentalSettingsApi
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.gdg.fraud_cmp_app.data.data_source.local.dao.BookDao
import org.gdg.fraud_cmp_app.data.data_source.local.dao.BookDaoImpl
import org.gdg.fraud_cmp_app.data.data_source.remote.HttpClientFactory
import org.gdg.fraud_cmp_app.data.data_source.settings.SettingsRepositoryImpl
import org.gdg.fraud_cmp_app.domain.repository.AppRepository
import org.gdg.fraud_cmp_app.domain.repository.SettingsRepository
import org.gdg.fraud_cmp_app.domain.use_case.AddBook
import org.gdg.fraud_cmp_app.domain.use_case.DeleteBook
import org.gdg.fraud_cmp_app.domain.use_case.GetAlreadyRead
import org.gdg.fraud_cmp_app.domain.use_case.GetBookDetails
import org.gdg.fraud_cmp_app.domain.use_case.GetCartItems
import org.gdg.fraud_cmp_app.domain.use_case.GetCurrentlyReading
import org.gdg.fraud_cmp_app.presentation.cart.CartViewModel

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
    factory<GetCurrentlyReading> { GetCurrentlyReading(get()) }
    factory<GetAlreadyRead> { GetAlreadyRead(get()) }
    factory<GetBookDetails> { GetBookDetails(get()) }
    factory<GetCartItems> { GetCartItems(get()) }
    factory<AddBook> { AddBook(get()) }
    factory<DeleteBook> { DeleteBook(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(observableSettings = get()) }
    singleOf(::CartViewModel)
}

expect fun platformModule(): Module
