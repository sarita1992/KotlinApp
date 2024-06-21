package com.example.viesuretest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


@HiltAndroidApp
class ViesureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@ViesureApp)
            modules(
                listOf(appModules)
            )
        }
    }
}