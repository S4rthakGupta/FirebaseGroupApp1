package com.example.firebasegroupapp1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasegroupapp1.databinding.ActivityThankyouBinding
import com.google.firebase.auth.FirebaseAuth

class ThankyouActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThankyouBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThankyouBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the Toolbar
        val toolbar = binding.navibar // Replace 'navibar' with the ID of your Toolbar in XML
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable the Back button

        // Handle Back button click
        toolbar.setNavigationOnClickListener {
            finish() // Close the current activity and return to the previous one
        }
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
