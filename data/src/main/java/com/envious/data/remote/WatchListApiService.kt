package com.envious.data.remote

import com.envious.data.remote.response.WatchListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchListApiService {

    @GET("totaltoptiervolfull")
    suspend fun getWatchList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("tsym") tsym: String
    ): Response<WatchListResponse>
}
