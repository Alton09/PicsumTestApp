package com.example.hsexercise.feature

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.hsexercise.feature.database.FeatureModel
import com.example.hsexercise.feature.database.FeatureTableDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FeatureRepository(
    private val featureNetworkDataSource: FeatureNetworkDataSource,
    private val dao: FeatureTableDao,
    private val cm: ConnectivityManager,
    private val coroutineScope: CoroutineContext = Dispatchers.IO
) {

    suspend fun getPics(): List<FeatureModel> {
        // TODO
        // To update stale data in the database, store a timestamp to allow the app to fetch data at a future time
        var result = dao.coGetAll()
        if(isNetworkConnected()) {
            withContext(coroutineScope) {
                if(result.isEmpty()) {
                    result = featureNetworkDataSource.getPics()
                    dao.insertAll(result)
                    result = dao.coGetAll()
                }
            }
        }

        return result
    }

    private fun isNetworkConnected(): Boolean {
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}