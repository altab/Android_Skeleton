package fr.pcohen.app.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.pcohen.app.R
import fr.pcohen.app.api.RegisterDto
import fr.pcohen.app.services.AppServiceWrapper
import fr.pcohen.app.services.AuthenticationTokenInterceptor
import fr.pcohen.app.viewmodels.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val authenticationTokenInterceptor: AuthenticationTokenInterceptor,
    private val appService: AppServiceWrapper
) : ViewModel() {

    enum class RegisterViewModelState {
        IDLE, LOADING, SUCCESS
    }

    var isProfessional: Boolean = false

    val state: LiveData<RegisterViewModelState>
        get() = _state
    val errorMessageEvent = MutableLiveData<Event<String>>()
    val errorMessageResourceEvent = MutableLiveData<Event<Int>>()

    private val _state = MutableLiveData(RegisterViewModelState.IDLE)

    fun register(registerDto: RegisterDto, confirmationPassword: String) {
        if (registerDto.email.isBlank() || registerDto.password.isBlank() || registerDto.phone.isBlank() ||
            (isProfessional && (registerDto.company.isNullOrBlank() || registerDto.identificationCode.isNullOrBlank()))) {
            errorMessageResourceEvent.value = Event(R.string.fill_all_fields)
            return
        }
        if (registerDto.password != confirmationPassword) {
            errorMessageResourceEvent.value = Event(R.string.different_password)
            return
        }
        if (!registerDto.legalTerms) {
            errorMessageResourceEvent.value = Event(R.string.cgu_required)
            return
        }
        _state.value = RegisterViewModelState.LOADING
        viewModelScope.launch {
            val response = appService.register(registerDto)
            _state.value = RegisterViewModelState.IDLE
            val body = response?.body()
            Timber.d("Register response = ${response?.code()}")
            if (body == null) {
                errorMessageResourceEvent.value = Event(R.string.check_internet)
                return@launch
            }
            if (body.token != null) {
                authenticationTokenInterceptor.token = body.token
                _state.value = RegisterViewModelState.SUCCESS
                return@launch
            }

            errorMessageEvent.value = Event("Une erreur est survenue")
        }
    }
}