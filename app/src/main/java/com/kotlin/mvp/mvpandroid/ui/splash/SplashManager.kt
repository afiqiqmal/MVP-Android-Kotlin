package com.kotlin.mvp.mvpandroid.ui.splash

import com.java.mvp.factory.entity.request.TokenRequest
import com.java.mvp.factory.entity.response.TokenResponse
import com.java.mvp.factory.internal.RestApi
import com.kotlin.mvp.mvpandroid.ui.common.mvp.BaseManager
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
class SplashManager

@Inject
constructor(api: RestApi) : BaseManager() {

    init {
        restApi = api
    }

    fun getToken(request: TokenRequest): Observable<TokenResponse> {
        return restApi!!.getToken(request)
    }
}

