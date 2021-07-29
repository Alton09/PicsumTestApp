package com.example.hsexercise.feature

import com.example.hsexercise.feature.database.FeatureModel

data class FeatureViewState(
    val isLoading: Boolean = false,
    val list: List<FeatureModel> = emptyList(),
    // TODO Errors should be part of a separate one time view event stream (i.e. a ViewEffect) and not the state
    val error: ErrorState? = null
)

sealed class ErrorState {
    object NetworkError: ErrorState()
    object EmptyListError: ErrorState()
}