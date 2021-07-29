package com.example.hsexercise.feature

import com.example.hsexercise.feature.database.FeatureModel

class FeatureRepository(
    private val featureNetworkDataSource: FeatureNetworkDataSource,
//    private val dao: FeatureTableDao
    ) {

    suspend fun getPics(): List<FeatureModel> {
        // TODO
        // Check network
            // if there is network
                // getPics from database
                    // don't exist then fetch from network, update database on response
        // To update stale data in the database, store a timestamp to allow the app to fetch data at a future time

        return featureNetworkDataSource.getPics()
    }
}