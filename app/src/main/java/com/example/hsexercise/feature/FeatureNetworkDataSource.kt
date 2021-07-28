package com.example.hsexercise.feature

import com.example.hsexercise.feature.database.FeatureModel
import retrofit2.http.GET

interface FeatureNetworkDataSource {

    @GET("v2/list")
    suspend fun getPics(): List<FeatureModel>
}