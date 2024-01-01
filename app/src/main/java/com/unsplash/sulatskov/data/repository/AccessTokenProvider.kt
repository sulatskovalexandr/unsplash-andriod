package com.unsplash.sulatskov.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.unsplash.sulatskov.domain.model.AccessToken
import com.unsplash.sulatskov.domain.model.Me
import javax.inject.Inject

class AccessTokenProvider @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("filename", Context.MODE_PRIVATE)

    val clientId = //"uFAuiJArob6B1GyyS9uKWHQhpOgyIpFJwD548UePxD4"
        sharedPreferences.getString(TOKEN_KEY, null).isNullOrBlank()
    val clientSecret = //"_xt7DU1OizkNjbwiA8N2-5ip7hif5LZyGxN2MJXaE_g"
        sharedPreferences.getString(TOKEN_KEY, null).isNullOrBlank()

    val isAuthorized: Boolean
        get() = !accessToken.isNullOrEmpty()


    var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        set(value) {
            sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, value).apply()
        }

    val userName: String?
        get() = sharedPreferences.getString(USERNAME_KEY, null)

    val email: String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)

    val profileImage: String?
        get() = sharedPreferences.getString(PROFILE_IMAGE_KEY, null)

    fun saveAccessToken(accessToken: AccessToken) = sharedPreferences.edit {
        putString(ACCESS_TOKEN_KEY, accessToken.accessToken)
            .apply()
    }

    fun saveUserProfile(me: Me) = sharedPreferences.edit {
        putString(USERNAME_KEY, me.userName)
        putString(EMAIL_KEY, me.email)
        putString(PROFILE_IMAGE_KEY, me.profileImage.large)
            .apply()
    }

    fun reset() = sharedPreferences.edit {
        putString(ACCESS_TOKEN_KEY, null)
        putString(USERNAME_KEY, null)
        putString(EMAIL_KEY, null)
        putString(PROFILE_IMAGE_KEY, null)
            .apply()
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val TOKEN_KEY = "token"

        private const val USERNAME_KEY = "user_username"
        private const val EMAIL_KEY = "user_email"
        private const val PROFILE_IMAGE_KEY = "user_profile_image"
    }
}
