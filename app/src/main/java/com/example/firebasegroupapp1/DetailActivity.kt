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

class DetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailBinding
    private var Price :Double = 0.0

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

        findViewById<Button>(R.id.btnBuyNow).setOnClickListener {
            BuyNow()
        }

        val imageView = findViewById<ImageView>(R.id.imgDish)
        Glide.with(this).load(DishImage).into(imageView)

    }

    private fun BuyNow(){
        val intent = Intent(this@DetailActivity, CheckoutActivity::class.java)
        intent.putExtra("Price", Price)
        startActivity(intent)
    }
}