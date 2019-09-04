package com.emi.retrofitpushdemo

import io.reactivex.Observable
import retrofit2.http.*

interface NetworkServices {
    //id, name, email, age, city

    @FormUrlEncoded
    @POST("/posts")
    @Headers("Cache-Control: max-age=640000")
    fun saveForm(@Field("id")  id : Int?, @Field("name") name : String?,
                 @Field("email") email : String?, @Field("age") age : Int?,
                 @Field("city") city : String?) : Observable<Profile>

    @GET("/posts/{id}")
    fun getUserId(@Path("id") id : Int?) : Observable<Profile>

}