package dev.com.trykotlin.test.main

import android.app.Application
import dev.com.trykotlin.test.data.sharepref.SP

class MainApp: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        SP.getInstance().init(this)
    }
}