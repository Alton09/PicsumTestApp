package com.example.hsexercise.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hsexercise.R
import com.example.hsexercise.feature.database.FeatureModel

// TODO Could use ListAdapter with DiffUtils to get better performance and animations
class FeatureAdapter: RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {
    private var data: List<FeatureModel> = emptyList()

    fun swap(data: List<FeatureModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val picItemView: ConstraintLayout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pic_item, parent, false) as ConstraintLayout
        return FeatureViewHolder(picItemView)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val dataItem = data[position]
        holder.picItemView.apply {
            (getChildAt(0) as TextView).text = dataItem.author
            (getChildAt(1) as TextView).text = context.getString(R.string.dimensions, dataItem.width, dataItem.height)
            // TODO Load image with glide
        }
    }

    override fun getItemCount() = data.size

    class FeatureViewHolder(val picItemView: ConstraintLayout) : RecyclerView.ViewHolder(picItemView)
}