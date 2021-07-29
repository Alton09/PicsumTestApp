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

    /*
     Realized that updating the stale database data is actually pretty straight forward.
     Simply update the database from the network if the user performs a swipe to refresh.
     */
    suspend fun getPics(isRefresh: Boolean): List<FeatureModel> {
        var result = dao.coGetAll()
        if(isNetworkConnected()) {
            if(isRefresh) {
                result = updateDatabaseFromNetwork()
            } else {
                if(result.isEmpty()) {
                    result = updateDatabaseFromNetwork()
                }
            }
        }

        return result
    }

    private fun isNetworkConnected(): Boolean {
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private suspend fun updateDatabaseFromNetwork(): List<FeatureModel> {
        withContext(coroutineScope) {
            val networkResult = featureNetworkDataSource.getPics()
            dao.insertAll(networkResult)
        }
        return dao.coGetAll()
    }
}