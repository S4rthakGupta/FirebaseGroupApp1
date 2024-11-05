package com.example.firebasegroupapp1

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.firebasegroupapp1.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCheckoutBinding
    private var Price : Double = 0.0
    private var tax : Double = 0.0
    private var totalPrice : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Price = intent.getDoubleExtra("Price", 0.0)
        tax = calculateTax(Price.toDouble())
        totalPrice = Price + tax

        findViewById<TextView>(R.id.txtTotalPrice).text = "Total Price: $${Price}"
        findViewById<TextView>(R.id.txtTax).text = "Tax (13%): $${tax}"
        findViewById<TextView>(R.id.txtTotalAmt).text = "Total Amount: $${totalPrice}"

        findViewById<Button>(R.id.btnPayNow).setOnClickListener {
            if (validateFields()) {
            val intent = Intent(this@CheckoutActivity, ThankyouActivity::class.java)
            startActivity(intent)
        }
        }
    }

    fun calculateTax(price: Double): Double {
        val taxRate = 0.13
        return price * taxRate
    }

    private fun validateFields(): Boolean {
        val firstNameField = findViewById<EditText>(R.id.editTextFirstName)
        val emailField = findViewById<EditText>(R.id.editTextEmail)
        val phoneField = findViewById<EditText>(R.id.editTextPhoneNo)
        val streetNo = findViewById<EditText>(R.id.editTextStreetNo)
        val cityField = findViewById<EditText>(R.id.editTextCity)
        val postalCode = findViewById<EditText>(R.id.editTextPostalCode)
        val cardNumberField = findViewById<EditText>(R.id.editTextCardNumber)

        return when {
            firstNameField.text.isBlank() -> {
                firstNameField.error = "Full Name is required"
                false
            }
            !isValidEmail(emailField.text.toString()) -> {
                emailField.error = "Invalid Email"
                false
            }
            !isValidPhone(phoneField.text.toString()) -> {
                phoneField.error = "Invalid Phone Number"
                false
            }
            streetNo.text.isBlank() -> {
                streetNo.error = "Street Number is required"
                false
            }
            cityField.text.isBlank() -> {
                cityField.error = "City is required"
                false
            }
            !isValidPostalCode(postalCode.text.toString()) -> {
                postalCode.error = "Invalid Postal Code"
                false
            }
            !isValidCardNumber(cardNumberField.text.toString()) -> {
                cardNumberField.error = "Invalid Card Number"
                false
            }
            else -> true
        }
    }

    // Email validation
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Phone validation
    private fun isValidPhone(phone: String): Boolean {
        return !TextUtils.isEmpty(phone) && phone.length == 10 && Patterns.PHONE.matcher(phone).matches()
    }

    // Postal code validation Canadian format
    private fun isValidPostalCode(postalCode: String): Boolean {
        val canadianPostalCodePattern = "^[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$"
        return postalCode.matches(canadianPostalCodePattern.toRegex())
    }

    // Card number validation helper
    private fun isValidCardNumber(cardNumber: String): Boolean {
        return cardNumber.length in 13..19 && cardNumber.all { it.isDigit() }
    }
}