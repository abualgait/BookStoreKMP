package org.gdg.fraud_cmp_app.di

import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.gdg.fraud_cmp_app.data.data_source.remote.HttpClientFactory
import org.gdg.fraud_cmp_app.data.repository.AppRepositoryImpl
import org.gdg.fraud_cmp_app.domain.repository.AppRepository
import org.gdg.fraud_cmp_app.domain.use_case.CheckSms
import org.gdg.fraud_cmp_app.presentation.frauddetection.FraudDetectionViewModel

@OptIn(ExperimentalSettingsApi::class)
fun commonModule(enableNetworkLogs: Boolean) = module {
    /**
     * Creates a http client for Ktor that is provided to the
     * API client via constructor injection
     */
    single {
        HttpClientFactory.makeClient(enableNetworkLogs = enableNetworkLogs)
    }

    single<AppRepository> { AppRepositoryImpl(httpClient = get()) }
    factory<CheckSms> { CheckSms(get()) }

    singleOf(::FraudDetectionViewModel)
}