import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasegroupapp1.DetailActivity
import com.example.firebasegroupapp1.DishActivity
import com.example.firebasegroupapp1.R

import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Use a simple splash screen layout with your logo

        // Delay for the splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                // If no user is signed in, go to SignUpActivity
                startActivity(Intent(this, SignUpActivity::class.java))
            } else {
                // If user is signed in, go to HomeActivity or main content
                startActivity(Intent(this, DishActivity::class.java))
            }
            finish() // Close the splash screen
        }, 2000) // 2-second delay
    }
}
