package com.natanborges.smartposgertec

import android.app.Application
import com.natanborges.smartposgertec.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SmartPosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SmartPosApplication)
            modules(appModule)
        }
    }
}