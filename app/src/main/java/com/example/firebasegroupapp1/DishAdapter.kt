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
        Glide.with(holder.dishImg.context)
            .load(storageReference)
            .into(holder.dishImg)
    }
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.activity_product_list, parent, false)) {
        val productName: TextView = itemView.findViewById(R.id.dishName);
        val productPrice: TextView = itemView.findViewById(R.id.dishPrice);
        val dishImg: ImageView = itemView.findViewById<ImageView>(R.id.dishImg);

    }

}


