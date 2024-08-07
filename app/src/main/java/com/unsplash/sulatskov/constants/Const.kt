package com.unsplash.sulatskov.constants

object Const {
    const val BASE_URL = "https://api.unsplash.com/"
    const val YOUR_ACCESS_KEY = "uFAuiJArob6B1GyyS9uKWHQhpOgyIpFJwD548UePxD4"
    const val YOUR_SECRET_KEY = "uFAuiJArob6B1GyyS9uKWHQhpOgyIpFJwD548UePxD4"
    const val UNSPLASH_DIRECTORY = "Unsplash"

    const val unsplashAuthCallback: String = "unsplash-auth-callback"
    const val REDIRECT_URI = "unsplash://$unsplashAuthCallback"
    const val GRANT_TYPE = "authorization_code"

    const val loginUrl = "https://unsplash.com/oauth/authorize" +
            "?client_id=$YOUR_ACCESS_KEY" +
            "&redirect_uri=unsplash%3A%2F%2F$unsplashAuthCallback" +
            "&response_type=code" +
            "&scope=public+read_user+write_user+read_photos+write_photos" +
            "+write_likes+write_followers+read_collections+write_collections"

    const val PER_PAGE = 10
    const val PHOTO_ID_KEY = "PHOTO_ID_KEY"
    const val PHOTO_URL_KEY = "PHOTO_URL_KEY"
    const val PHOTO_PROFILE_KEY = "PHOTO_PROFILE_KEY"
    const val USER_NAME_KEY = "USER_NAME_KEY"
    const val TITLE_KEY = "TITLE_KEY"
    const val COLLECTION_ID_KEY = "COLLECTION_ID_KEY"
}