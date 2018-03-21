package com.java.mvp.factory.internal


import com.java.mvp.factory.entity.request.TokenRequest
import com.java.mvp.factory.entity.response.TokenResponse

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author : hafiq on 23/01/2017.
 */

interface RestApi {

    @POST("token")
    fun getToken(@Body request: TokenRequest): Observable<TokenResponse>

}


