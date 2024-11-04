package com.example.firebasegroupapp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasegroupapp1.databinding.ActivityDishBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class ProductActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDishBinding


    private var adapter: ProductAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query = FirebaseDatabase.getInstance().reference.child("Dish")
        val options = FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product::class.java).build()

        adapter = ProductAdapter(options)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
    override fun onStart()
    {
        super.onStart()
        adapter?.startListening()
    }


}