package com.example.firebasegroupapp1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasegroupapp1.databinding.ActivityProductBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProductActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser
    private lateinit var binding: ActivityProductBinding
    private var adapter: ProductAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar with the new ID
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.navibar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button

        // Firebase query setup
        val query = FirebaseDatabase.getInstance().reference.child("Dish")
        val options = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()

        adapter = ProductAdapter(options)
        // Toast.makeText(this, user?.uid ?: "User not logged in", Toast.LENGTH_SHORT).show()

        // RecyclerView setup
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    // onStart method should be outside onCreate
    override fun onStart() {
        super.onStart()
        adapter?.startListening()

    }

    override fun onResume() {
        super.onResume()
        adapter?.startListening()
        adapter?.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        recyclerView?.adapter = null
        adapter?.stopListening()
    }

    // Inflate the menu (Sign Out button)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_menu, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back button
                finish()
                return true
            }
            R.id.action_sign_out -> {
                // Handle sign-out
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
