package fr.pcohen.app.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fr.pcohen.app.ui.home.ProfileViewModel
import fr.pcohen.app.ui.login.LoginViewModel
import fr.pcohen.app.ui.register.RegisterViewModel
import fr.pcohen.app.viewmodels.AppViewModelFactory

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

}