package com.example.hsexercise.common

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class HsExerciseGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
