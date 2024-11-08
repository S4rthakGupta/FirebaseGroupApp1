package com.example.firebasegroupapp1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var database: DatabaseReference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "DefaultUser"
    private var TotalPrice :Double = 0.0
    private var totalCartPrice :Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        

        // Set up the Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.navibar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button

        // Handle Back button click
        toolbar.setNavigationOnClickListener {
            finish() // Go back to the previous activity
        }

        database = FirebaseDatabase.getInstance().reference.child("users").child(uid).child("cart")

        recyclerView = findViewById(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadCartItems()

        val checkoutButton = findViewById<Button>(R.id.checkoutButton)

        checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("Price", totalCartPrice)
            startActivity(intent)
        }
    }

    private fun loadCartItems() {
        val cartItems = mutableListOf<CartItem>()
        totalCartPrice = 0.0
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                totalCartPrice = 0.0
                for (itemSnapshot in snapshot.children) {
                    val cartItem = itemSnapshot.getValue(CartItem::class.java)
                    cartItem?.key = itemSnapshot.key
                    if (cartItem != null) {
                        cartItems.add(cartItem)
                        totalCartPrice += cartItem.totalPrice ?: 0.0
                    }
                }
                cartAdapter = CartAdapter(cartItems, database)
                recyclerView.adapter = cartAdapter

                findViewById<TextView>(R.id.txtTotalPrice).text = "Total: $${"%.2f".format(totalCartPrice)}"

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CartActivity, "Failed to load cart items.", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onResume() {
        super.onResume()
        // Reload cart items when the activity resumes
        loadCartItems()
    }

//    // Stop Firebase listeners when activity is not visible
//    override fun onStop() {
//        super.onStop()
//        recyclerView.adapter = null // Clear the adapter to release resources
//    }

    // Inflate the menu (optional)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_menu, menu) // Use the same menu as ProductActivity
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_sign_out -> {
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