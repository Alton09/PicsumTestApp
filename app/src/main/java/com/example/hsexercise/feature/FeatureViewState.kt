package com.example.hsexercise.feature

data class FeatureViewState(
    val isLoading: Boolean = false,
    val list: List<PicViewState> = emptyList(),
    val error: ErrorState? = null
)

data class PicViewState(
    val author: String = "",
    val width: Int = 0,
    val height: Int,
    val downloadURL: String,
)

sealed class ErrorState {
    object NetworkError
}