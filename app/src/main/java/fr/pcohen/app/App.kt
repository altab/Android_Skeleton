package fr.pcohen.app

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DaggerApplication
import fr.pcohen.app.dagger.DaggerAppComponent
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector() = DaggerAppComponent.factory().create(this)
}