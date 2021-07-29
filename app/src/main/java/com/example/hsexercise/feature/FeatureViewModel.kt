package com.example.hsexercise.feature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeatureViewModel(private val featureRepository: FeatureRepository) : ViewModel() {
    private val TAG = "FeatureViewModel"
    private val mutableViewState: MutableLiveData<FeatureViewState> = MutableLiveData(FeatureViewState())
    val viewState: LiveData<FeatureViewState> = mutableViewState

    class Factory(private val featureRepository: FeatureRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
        ) = FeatureViewModel(featureRepository) as T
    }

    fun getImages() {
        viewModelScope.launch {
            updateState { it.copy(
                isLoading = true
            ) }
            val pics = featureRepository.getPics()
            updateState { it.copy(
                list = pics
                )
            }
        }
    }

    private fun updateState(action: (oldState: FeatureViewState) -> FeatureViewState) {
        withState { currentState ->
            val newState = action(currentState)
            mutableViewState.value = newState
            Log.d(TAG,"updateState = $newState")
        }
    }

    private fun <T> withState(action: (currentState: FeatureViewState) -> T) =
        // Force unwrap used since an initial view state is always set
        action(mutableViewState.value!!)
}
