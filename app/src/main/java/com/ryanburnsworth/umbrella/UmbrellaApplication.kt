package com.ryanburnsworth.umbrella

import android.app.Application
import com.ryanburnsworth.umbrella.data.APIServicesProvider
import com.ryanburnsworth.umbrella.ui.viewmodel.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class UmbrellaApplication : Application() {

    private val modules = module {
        single { APIServicesProvider() }
        viewModel { MainActivityViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@UmbrellaApplication)
            modules(modules)
        }
    }
}
