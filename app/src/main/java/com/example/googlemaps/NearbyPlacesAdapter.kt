package com.example.googlemaps

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.maps.model.PlacesSearchResult

internal class NearbyPlacesAdapter(private var list: Array<PlacesSearchResult>) :
    RecyclerView.Adapter<NearbyPlacesAdapter.MyViewHolder>() {
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nearby_place, parent, false)
        return MyViewHolder(itemView)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvPlaceName.text = (position+1).toString()+". " + list[position].name
    }
    override fun getItemCount(): Int {
        return list.size
    }


    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvPlaceName: TextView = view.findViewById(R.id.tvPlaceName)
    }
}