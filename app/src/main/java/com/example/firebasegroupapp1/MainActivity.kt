package com.example.firebasegroupapp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.firebasegroupapp1.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val btnNext: Button = findViewById(R.id.splashBtn)
        btnNext.setOnClickListener {

            val i = Intent(
                this,
                ProductActivity::class.java
            )
            this.startActivity(i)
        }

        if (auth.currentUser == null)
        {
            createSignInIntent()
        } else {
            Log.d("Current User", "User Email: ${auth.currentUser?.email}")

        }
    }


    override fun onStart() {
        super.onStart()
        var currentUser = auth.currentUser
        if (currentUser != null) {
        }
        else {
            Toast.makeText(this, "NO USER LOGGED IN - ON START", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if
                (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser

        } else {
            createSignInIntent()
        }
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val signInIntent =
            AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false)
                .setAvailableProviders(providers).build()
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract())
        { result ->
            this.onSignInResult(result)
        }


}
