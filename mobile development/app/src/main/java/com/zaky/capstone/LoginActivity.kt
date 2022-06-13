package com.zaky.capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zaky.capstone.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding


    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        //Autentikasi Firebase
        auth = Firebase.auth

        //Cek Login
        val user = Firebase.auth.currentUser
        if (user != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        } else {


            //UI Processing
            var hide = true
            binding.mata.setOnClickListener {
                if (hide == true) {
                    binding.password.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.mata.setImageResource(R.drawable.ic_hide)
                    hide = false
                } else {
                    binding.password.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.mata.setImageResource(R.drawable.ic_show)
                    hide = true
                }

            }

            binding.tvDaftar.setOnClickListener {
                val i = Intent(this, SignUpActivity::class.java)
                startActivity(i)
                finish()
            }

            binding.login.setOnClickListener {
                loadingMulai()
                val email = binding.username.text.toString()
                val password = binding.password.text.toString()
                if(email == "" || email == null) {
                    binding.username.error = "Email masih kosong"
                } else {
                    if (!isValidEmailId(email)) {
                        binding.username.error = "Email tidak valid"
                    } else {
                        if (password == "" || password == null) {
                            binding.password.error = "Password masih kosong"
                        } else {
                            login(email, password)
                        }
                    }
                }


            }
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
        }
    }

    private fun loadingMulai() {
        binding.login.text = "Tunggu sebentar"
        binding.login.isEnabled = false
    }
    private fun loadingSelesai() {
        binding.login.text = "Masuk"
        binding.login.isEnabled = true
    }


    private fun login (email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Snackbar.make(binding.root, "Login gagal", Snackbar.LENGTH_LONG).show()
                    loadingSelesai()
                }
            }
            .addOnFailureListener {
                Snackbar.make(binding.root, "Login gagal", Snackbar.LENGTH_LONG).show()
                loadingSelesai()
            }
    }
    companion object {
        var TAG = "Login Activity";
    }
    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}