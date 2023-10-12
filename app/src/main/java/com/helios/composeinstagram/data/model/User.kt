package com.helios.composeinstagram.data.model

data class User(
    var userId: String? = null,
    var name: String? = null,
    var username: String? = null,
    var email: String? = null,
    var imageUrl: String? = null,
    var bio: String? = null,
    var following: List<String>? = null
) {
    fun toMap() = mapOf(
        USER_ID to userId,
        NAME to name,
        EMAIL to email,
        USERNAME to username,
        IMAGE_URL to imageUrl,
        BIO to bio,
        FOLLOWING to following
    )

    companion object {
        const val USER_ID = "userId"
        const val NAME = "name"
        const val EMAIL = "email"
        const val USERNAME = "username"
        const val IMAGE_URL = "imageUrl"
        const val BIO = "bio"
        const val FOLLOWING = "following"
    }
}