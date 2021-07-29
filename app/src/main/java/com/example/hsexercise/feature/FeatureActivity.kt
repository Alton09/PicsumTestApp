package com.example.hsexercise.feature

import android.app.AlertDialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsexercise.R
import com.example.hsexercise.common.BaseActivity
import com.example.hsexercise.common.NetworkProvider

class FeatureActivity : BaseActivity<FeatureViewModel>() {

    override val viewModelClass = FeatureViewModel::class.java
    override val layoutResId = R.layout.activity_feature
    private var featureRepository: FeatureRepository? = null
    private var featureAdapter: FeatureAdapter = FeatureAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Move repository construction to a Dependency Injection framework
       val restClient = NetworkProvider.provideRestClient()
        val service = restClient.createRetrofitAdapter().create(FeatureNetworkDataSource::class.java)
        featureRepository = FeatureRepository(service)

        super.onCreate(savedInstanceState)

        viewModel.viewState.observe(this, { renderViewStates(it) })

        // Setup recycler view
        findViewById<RecyclerView>(R.id.pics_list).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featureAdapter
        }
    }

    override fun provideViewModelFactory() = FeatureViewModel.Factory(featureRepository!!)

    override fun onViewLoad(savedInstanceState: Bundle?) {
        viewModel.getImages()
    }

    private fun renderViewStates(featureViewState: FeatureViewState) {
        featureAdapter.swap(featureViewState.list)
        if(featureViewState.error != null) {
            AlertDialog
                .Builder(this)
                .setMessage("A network error has occurred. Please check the network and try again.")
                .setNeutralButton("Okay") { _,_ -> }
                .show()
        }
    }
}
