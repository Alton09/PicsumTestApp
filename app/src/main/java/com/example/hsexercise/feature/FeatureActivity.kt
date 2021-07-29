package com.example.hsexercise.feature

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hsexercise.R
import com.example.hsexercise.common.BaseActivity
import com.example.hsexercise.common.DatabaseProvider
import com.example.hsexercise.common.NetworkProvider

class FeatureActivity : BaseActivity<FeatureViewModel>() {

    override val viewModelClass = FeatureViewModel::class.java
    override val layoutResId = R.layout.activity_feature
    private var featureRepository: FeatureRepository? = null
    private val featureAdapter: FeatureAdapter = FeatureAdapter()
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Initialize and manage dependencies with a Dependency Injection framework
        val restClient = NetworkProvider.provideRestClient()
        val service = restClient.createRetrofitAdapter().create(FeatureNetworkDataSource::class.java)
        val dao = DatabaseProvider.provideRoomDatabase(application).featureTableDao()
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        featureRepository = FeatureRepository(service, dao, cm)

        super.onCreate(savedInstanceState)

        viewModel.viewState.observe(this, { renderViewStates(it) })

        // Setup recycler view
        findViewById<RecyclerView>(R.id.pics_list).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featureAdapter
        }
        refreshLayout = findViewById(R.id.refresh_layout)
        refreshLayout.apply {
            setOnRefreshListener { viewModel.getImages() }
        }
    }

    override fun provideViewModelFactory() = FeatureViewModel.Factory(featureRepository!!)

    override fun onViewLoad(savedInstanceState: Bundle?) {
        viewModel.getImages()
    }

    private fun renderViewStates(featureViewState: FeatureViewState) {
        featureAdapter.swap(featureViewState.list)
        when (featureViewState.error) {
            ErrorState.NetworkError ->
                AlertDialog
                    .Builder(this)
                    // TODO Add string resources for these literals
                    .setMessage("A network error has occurred. Please check the network and try again.")
                    .setNeutralButton("Okay") { _,_ -> }
                    .show()
            ErrorState.EmptyListError -> {
                AlertDialog
                    .Builder(this)
                    // TODO Add string resources for these literals
                    .setMessage("There were no images to display. Please try again later")
                    .setNeutralButton("Okay") { _,_ -> }
                    .show()
            }
            null -> {}
        }
        refreshLayout.isRefreshing = featureViewState.isLoading
    }
}
