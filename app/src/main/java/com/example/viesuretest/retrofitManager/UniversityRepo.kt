package com.example.viesuretest.retrofitManager

import com.example.viesuretest.model.BookData
import com.example.viesuretest.utils.UseCaseResult


interface UniversityRepo {


    suspend fun getBookData(): UseCaseResult<ArrayList<BookData>>

}

class UniversityRepositoryImpl(private val api: ApiInterface) : UniversityRepo {


    override suspend fun getBookData(): UseCaseResult<ArrayList<BookData>> {
        return try {
            val result = api.getBookData().await()
            UseCaseResult.Success(result)

        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

}