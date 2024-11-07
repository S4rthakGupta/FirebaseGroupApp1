package com.example.firebasegroupapp1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.firebasegroupapp1.databinding.ActivityDetailBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailBinding
    private var Price :Double = 0.0
    private var quantity = 1
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val DishName = intent.getStringExtra("DishName")
        Price = intent.getStringExtra("Price")?.toDouble() ?: 0.0
        val DishImage = intent.getStringExtra("DishImage")
        val Description = intent.getStringExtra("DishDesc")

        findViewById<TextView>(R.id.txtDishName).text = DishName
        findViewById<TextView>(R.id.txtPrice).text = "Price: $$Price"
        findViewById<TextView>(R.id.txtDescDetails).text = Description
        val imgDish = findViewById<ImageView>(R.id.imgDish)

        if (DishImage != null) {
            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(DishImage.toString())
            storageReference.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imgDish)
            }).addOnFailureListener {
            }
        }
        uid = FirebaseAuth.getInstance().currentUser?.uid ?: "DefaultUser"

        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val txtQuantity = findViewById<TextView>(R.id.txtQuantity)
        val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)
        val btnViewCart = findViewById<Button>(R.id.btnViewCart)
        val btnBuyNow = findViewById<Button>(R.id.btnBuyNow)

        btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                txtQuantity.text = quantity.toString()
            }
        }

        btnPlus.setOnClickListener {
            quantity++
            txtQuantity.text = quantity.toString()
        }

        btnAddToCart.setOnClickListener {
            addToCart(DishName ?: "Dish", Price, quantity)
        }

        btnViewCart.setOnClickListener {
            ViewCart()
        }

        btnBuyNow.setOnClickListener {
            BuyNow()
        }

        val imageView = findViewById<ImageView>(R.id.imgDish)
        Glide.with(this).load(DishImage).into(imageView)

    }
    private fun addToCart(dishName: String, price: Double, quantity: Int) {
        val database = FirebaseDatabase.getInstance().reference
        val cartItem = mapOf(
            "dishName" to dishName,
            "price" to price,
            "quantity" to quantity,
            "totalPrice" to price * quantity
        )
        database.child("users").child(uid).child("cart").push()
            .setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Added to Cart!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add to cart. Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun ViewCart(){
        val intent = Intent(this@DetailActivity, CartActivity::class.java)
        startActivity(intent)
    }

    private fun BuyNow(){
        val intent = Intent(this@DetailActivity, CheckoutActivity::class.java)
        intent.putExtra("Price", Price * quantity)
        startActivity(intent)
    }
}