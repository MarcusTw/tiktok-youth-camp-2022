package com.example.ttyc.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    companion object{
        private const val URL = "https://api.themoviedb.org"
        private var retrofit: Retrofit? = null

        fun getInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}