package fr.pcohen.app.services

import fr.pcohen.app.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppServiceWrapper @Inject constructor(private val appService: AppService) {

    suspend fun login(loginDto: LoginDto): Response<LoginResponseDto>?
        = ioContextExecutor { appService.login(loginDto) }

    suspend fun register(registerDto: RegisterDto): Response<TokenDto>?
        = ioContextExecutor { appService.register(registerDto) }

    suspend fun getProfile(): ProfileDto?
        = ioContextExecutor { appService.getProfile() }

    private suspend fun <T> ioContextExecutor(block: suspend () -> T): T? = withContext(Dispatchers.IO) {
        try {
            block()
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
    }

}