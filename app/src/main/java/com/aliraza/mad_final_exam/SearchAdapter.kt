package com.aliraza.mad_final_exam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.result_item.view.*

class SearchAdapter(var vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).asBitmap().load(vehicleList[position].images).apply(
            RequestOptions().placeholder(R.drawable.iu)
        ).into(holder.itemView.sImage)
        holder.itemView.sMake.text = vehicleList[position].make
        holder.itemView.sModel.text = vehicleList[position].model
        holder.itemView.sName.text = vehicleList[position].sname
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}