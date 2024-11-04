package com.example.firebasegroupapp1

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage

class ProductAdapter(options: FirebaseRecyclerOptions<Product>) :
    FirebaseRecyclerAdapter<Product, ProductAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductAdapter.MyViewHolder, position: Int, model: Product) {
        holder.productName.text = model.name;
        holder.productPrice.text = model.price.toString();
        val theImage: String = model.photo;

        if (model.photo.toString().startsWith("gs://")) {
            var storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(theImage)
            Glide.with(holder.itemView.context)
                .load(storageReference)
                .into(holder.dishImg)
        }
        else
            Glide.with(holder.itemView.context)
                .load(model.photo)
                .into(holder.dishImg)

        // Set the onClickListener to navigate to ProductDetailsActivity
        holder.RecyclerView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)

            // Pass product details to the ProductDetailsActivity
            intent.putExtra("productName", model.name)
            intent.putExtra("productPrice", model.price)
            intent.putExtra("productImage", model.photo)

            context.startActivity(intent)
        }
    }
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.activity_product_list, parent, false)) {
        val productName: TextView = itemView.findViewById(R.id.dishName);
        val productPrice: TextView = itemView.findViewById(R.id.dishPrice);
        val dishImg: ImageView = itemView.findViewById<ImageView>(R.id.dishImg);

    }

}


