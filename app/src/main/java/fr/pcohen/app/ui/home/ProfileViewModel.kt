package fr.pcohen.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.pcohen.app.api.ProfileDto
import fr.pcohen.app.services.AppServiceWrapper
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val appService: AppServiceWrapper
) : ViewModel() {

    val profile: LiveData<ProfileDto>
        get() = _profile

    private val _profile = MutableLiveData<ProfileDto>()

    init {
        Timber.d("Init ProfileViewModel")
        refreshProfile()
    }

    fun refreshProfile() {
        Timber.d("Refreshing profile...")
        viewModelScope.launch {
            appService.getProfile()?.let {
                _profile.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("ProfileViewModel onCleared")
    }
}