package com.example.firebasegroupapp1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage

class DishAdapter {
}



class PhoneAdapter(options: FirebaseRecyclerOptions<Phone>) :
    FirebaseRecyclerAdapter<Phone, PhoneAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PhoneAdapter.MyViewHolder, position: Int, model: Phone) {
        holder.txtBrand.text = model.Brand;
        holder.txtPrice.text = model.Price.toString();
        val theImage : String = model.Photo;

        var storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(theImage)
        Glide.with(holder.imgPhoto.context)
            .load(storageReference)
            .into(holder.imgPhoto)
    }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
        val txtBrand: TextView = itemView.findViewById(R.id.txtBrand);
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice);
        val imgPhoto: ImageView = itemView.findViewById<ImageView>(R.id.imgPhoto);

    }
}