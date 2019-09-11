package fr.pcohen.app.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import fr.pcohen.app.App
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, MainActivityModule::class])
interface AppComponent: AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: App): AppComponent
    }

}