package com.java.mvp.factory.entity.response

import com.google.gson.annotations.SerializedName

/**
 * @author : hafiq on 23/01/2017.
 */

class TokenResponse {

    val token: String? = null
    val message: String? = null

    @SerializedName("apps")
    val version: String? = null
}