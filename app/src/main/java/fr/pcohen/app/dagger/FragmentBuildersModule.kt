package fr.pcohen.app.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.pcohen.app.ui.about.AboutFragment
import fr.pcohen.app.ui.home.HomeFragment
import fr.pcohen.app.ui.login.LoginFragment
import fr.pcohen.app.ui.main.MainFragment
import fr.pcohen.app.ui.register.RegisterFragment
import fr.pcohen.app.ui.splash.SplashFragment

@Module
interface FragmentBuildersModule {

    @ContributesAndroidInjector
    fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeAboutFragment(): AboutFragment

}