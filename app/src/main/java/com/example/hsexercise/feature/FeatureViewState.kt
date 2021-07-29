package com.example.hsexercise.feature

import com.example.hsexercise.feature.database.FeatureModel

data class FeatureViewState(
    val isLoading: Boolean = false,
    val list: List<FeatureModel> = emptyList(),
    val error: ErrorState? = null
)

sealed class ErrorState {
    object NetworkError: ErrorState()
}