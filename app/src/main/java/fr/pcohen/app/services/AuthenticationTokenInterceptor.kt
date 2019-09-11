package fr.pcohen.app.services

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationTokenInterceptor @Inject constructor(
    private val sharedPreferencesService: SharedPreferencesService
): Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
        private const val AUTHORIZATION_PREFIX = "Bearer "
        const val AUTHORIZATION_HEADER = "$AUTHORIZATION_HEADER_NAME: dummy"
    }

    var token: String?
        get() = _token
        set(value) {
            _token = value
            prefixToken = "$AUTHORIZATION_PREFIX$_token"
            sharedPreferencesService.token = value
        }

    private var _token: String?
    private var prefixToken: String? = null

    init {
        _token = sharedPreferencesService.token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!request.header(AUTHORIZATION_HEADER_NAME).isNullOrEmpty()) {
            prefixToken?.let {
                request = request.newBuilder().header(AUTHORIZATION_HEADER_NAME, it).build()
            }
        }

        return chain.proceed(request)
    }
}