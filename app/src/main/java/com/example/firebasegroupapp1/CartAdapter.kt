package com.example.firebasegroupapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val database: DatabaseReference
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)

        holder.btnRemove.setOnClickListener {
            cartItem.key?.let {
                database.child(it).removeValue()
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDishName: TextView = itemView.findViewById(R.id.txtDishName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)

        fun bind(cartItem: CartItem) {
            txtDishName.text = cartItem.dishName
            txtPrice.text = "Price: $${cartItem.totalPrice}"
            txtQuantity.text = "Qty: ${cartItem.quantity}"
        }
    }
}
