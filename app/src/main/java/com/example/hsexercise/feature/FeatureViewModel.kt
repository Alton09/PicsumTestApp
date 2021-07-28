package com.example.hsexercise.feature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FeatureViewModel : ViewModel() {
    private val TAG = "FeatureViewModel"
    private val mutableViewState: MutableLiveData<FeatureViewState> = MutableLiveData(FeatureViewState())
    val viewState: LiveData<FeatureViewState> = mutableViewState

    class Factory :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FeatureViewModel() as T
    }


    fun getImages() {
        // TODO
    }

    private fun updateState(action: (oldState: FeatureViewState) -> FeatureViewState) {
        withState { currentState ->
            val newState = action(currentState)
            mutableViewState.value = newState
            Log.d(TAG,"updateState = $currentState")
        }
    }

    private fun withState(action: (currentState: FeatureViewState) -> Unit) =
        // Force unwrap used since an initial view state is always set
        action(mutableViewState.value!!)
}
