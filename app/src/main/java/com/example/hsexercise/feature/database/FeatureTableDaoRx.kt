package com.example.hsexercise.feature.database

import androidx.room.*
import com.google.gson.annotations.SerializedName
import io.reactivex.Maybe

@Dao
interface FeatureTableDao {
    @Query("SELECT * FROM feature")
    fun getAll(): Maybe<List<FeatureModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(models: List<FeatureModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(featureModel: FeatureModel)

    @Query("SELECT * FROM feature")
    suspend fun coGetAll(): List<FeatureModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun coInsertAll(models: List<FeatureModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun coInsert(featureModel: FeatureModel)
}

@Entity(tableName = "feature")
data class FeatureModel(
    @PrimaryKey
    val id: String,
    val author: String,
    @SerializedName("download_url")
    val url: String,
    val width: Int,
    val height: Int
)
