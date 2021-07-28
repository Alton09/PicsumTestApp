package com.example.hsexercise.feature

import android.os.Bundle
import com.example.hsexercise.R
import com.example.hsexercise.common.BaseActivity
import com.example.hsexercise.common.NetworkProvider

class FeatureActivity : BaseActivity<FeatureViewModel>() {

    override val viewModelClass = FeatureViewModel::class.java
    override val layoutResId = R.layout.activity_feature
    var repository: Repository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Move repository construction to a static level
       val restClient = NetworkProvider.provideRestClient()
        val service = restClient.createRetrofitAdapter().create(FeatureNetworkDataSource::class.java)
        repository = Repository(service)

        super.onCreate(savedInstanceState)


        viewModel.viewState.observe(this, { renderViewStates(it) })
        viewModel.getImages()
    }

    override fun provideViewModelFactory() = FeatureViewModel.Factory(repository!!)

    override fun onViewLoad(savedInstanceState: Bundle?) {
        // todo: write code here
    }

    private fun renderViewStates(featureViewState: FeatureViewState) {

    }
}
