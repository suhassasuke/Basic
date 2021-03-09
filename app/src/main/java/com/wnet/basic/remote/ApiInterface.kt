package com.biz2.nowfloats.boost.updates.data.remote

import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("/fjaqJ")
    fun GetCardInfo(): Observable<String>
}