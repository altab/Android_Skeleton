package fr.pcohen.app.api

import fr.pcohen.app.services.AuthenticationTokenInterceptor
import retrofit2.Response
import retrofit2.http.*

interface AppService {

    @POST("login")
    suspend fun login(@Body loginDto: LoginDto): Response<LoginResponseDto>

    @POST("register")
    suspend fun register(@Body registerDto: RegisterDto): Response<TokenDto>

    @GET("profile")
    @Headers(AuthenticationTokenInterceptor.AUTHORIZATION_HEADER)
    suspend fun getProfile(): ProfileDto

}