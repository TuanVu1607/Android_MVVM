package com.technology.android_mvvm.data.remote.api.example

import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.data.remote.api.example.request.ExampleRequest
import com.technology.android_mvvm.data.remote.response.ApiDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(Endpoints.GET_EXAMPLE_LIST_URL)
    suspend fun getExampleList(): ApiDataResponse<List<ExampleModel>>

    @GET(Endpoints.GET_EXAMPLE_DETAIL_URL)
    suspend fun getExampleDetail(
        @Query("id") id: Int,
    ): ApiDataResponse<ExampleModel>

    @POST(Endpoints.CREATE_EXAMPLE_URL)
    suspend fun createExample(
        @Body exampleRequest: ExampleRequest,
    ): ApiDataResponse<ExampleModel>
}