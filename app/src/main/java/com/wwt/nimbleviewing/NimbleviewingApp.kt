package com.wwt.nimbleviewing

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NimbleviewingApp : Application() {

    override fun onCreate() {

        super.onCreate()

        //sorry I am going to use hilt
        /*startKoin {
            modules(emptyList()) // TODO: do we need DI yet?
        }*/
    }
}