package fr.pcohen.app.api

data class LoginDto(val email: String, val password: String)

data class LoginResponseDto(val token: String?)

data class RegisterDto(val email: String, val password: String, val phone: String,
                       val legalTerms: Boolean, val company: String? = null, val identificationCode: String? = null)

data class TokenDto(val token: String?)

data class AddressDto(
    val addressEntered: String,
    val streetNumber: String?,
    val route: String?,
    val postalCode: String,
    val city: String,
    val department: String,
    val region: String,
    val country: String,
    val latitude: String,
    val longitude: String
)

data class ProfileDto(
    val identificationCode: String?,
    val company: String?,
    val isPro: Boolean,
    val email: String,
    val name: String,
    val firstname: String,
    val phone: String,
    val address: List<AddressDto>
)
