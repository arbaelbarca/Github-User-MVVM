package com.arbaelbarca.github_user_project

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arbaelbarca.github_user_project.di.myAppModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ExtendApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidLogger(Level.INFO)
            androidLogger(Level.ERROR)
            androidContext(this@ExtendApp)
            modules(myAppModule)
        }

        println("respon start koin")

        Hawk.init(this)
            .build()

    }
}