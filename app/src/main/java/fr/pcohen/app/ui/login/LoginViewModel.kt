package fr.pcohen.app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.pcohen.app.BuildConfig
import fr.pcohen.app.api.LoginDto
import fr.pcohen.app.services.AppServiceWrapper
import fr.pcohen.app.services.AuthenticationTokenInterceptor
import fr.pcohen.app.viewmodels.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authenticationTokenInterceptor: AuthenticationTokenInterceptor,
    private val appService: AppServiceWrapper
) : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED
    }

    enum class LoginState {
        FILL_FIELDS,
        NO_INTERNET,
        WRONG_CREDENTIALS,
        NOT_ACTIVATED
    }

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val authenticationState: LiveData<AuthenticationState>
        get() = _authenticationState
    val loginState: LiveData<Event<LoginState>>
        get() = _loginState

    private val _isLoading = MutableLiveData(false)
    private val _authenticationState = MutableLiveData<AuthenticationState>()
    private val _loginState = MutableLiveData<Event<LoginState>>()

    init {
        _authenticationState.value = if (authenticationTokenInterceptor.token == null) AuthenticationState.UNAUTHENTICATED else AuthenticationState.AUTHENTICATED
    }

    fun login(email: String?, password: String?) {
        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            _loginState.value = Event(LoginState.FILL_FIELDS)
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            val response = appService.login(LoginDto(email, password))
            _isLoading.value = false
            val body = response?.body()
            Timber.d("Login response = ${response?.code()}:$body")
            if (response == null) {
                _loginState.value = Event(LoginState.NO_INTERNET)
                return@launch
            }
            if (response.isSuccessful && body != null) {
                authenticationTokenInterceptor.token = body.token
                _authenticationState.value = AuthenticationState.AUTHENTICATED
                return@launch
            }

            if (response.code() == 403) {
                _loginState.value = Event(LoginState.NOT_ACTIVATED)
                return@launch
            }

            if (BuildConfig.DEBUG) {
                authenticationTokenInterceptor.token = "debug"
                _authenticationState.value = AuthenticationState.AUTHENTICATED
            } else {
                _loginState.value = Event(LoginState.WRONG_CREDENTIALS)
            }
        }
    }

    fun logout() {
        authenticationTokenInterceptor.token = null
        _authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}
