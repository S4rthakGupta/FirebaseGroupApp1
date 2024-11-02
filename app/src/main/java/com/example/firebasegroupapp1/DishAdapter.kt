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

class DishAdapter(options: FirebaseRecyclerOptions<Dish>) :
    FirebaseRecyclerAdapter<Dish, DishAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: DishAdapter.MyViewHolder, position: Int, model: Dish) {
        holder.productName.text = model.name;
        holder.productPrice.text = model.price.toString();
        val theImage : String = model.photo;

        var storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(theImage)
        Glide.with(holder.imgPhoto.context)
            .load(storageReference)
            .into(holder.imgPhoto)
    }
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
        val productName: TextView = itemView.findViewById(R.id.productName);
        val productPrice: TextView = itemView.findViewById(R.id.productPrice);
        val imgPhoto: ImageView = itemView.findViewById<ImageView>(R.id.imgPhoto);

    }

}


