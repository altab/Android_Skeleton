package fr.pcohen.app.dagger

import fr.pcohen.app.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    fun contributeMainActivity(): MainActivity

}
