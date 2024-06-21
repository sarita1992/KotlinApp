package com.example.viesuretest.retrofitManager

import com.example.viesuretest.model.BookData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by sarita
 */
interface ApiInterface {

    @GET(".")
    fun getBookData(): Deferred<ArrayList<BookData>>

  }