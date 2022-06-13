package com.zaky.capstone

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaky.capstone.data.User
import com.zaky.capstone.databinding.ActivitySignUpBinding
import com.zaky.capstone.ui.gallery.GalleryFragment
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.signUp.setOnClickListener {
            validasi()
            loadingMulai()
        }

        binding.tvLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }


    }

    private fun loadingMulai() {
        binding.signUp.text = "Tunggu Sebentar"
        binding.signUp.isEnabled = false
    }
    private fun loadingSelesai() {
        binding.signUp.text = "Daftar"
        binding.signUp.isEnabled = true
    }

    private fun validasi() {
        var name = binding.name.text.toString()
        var email = binding.username.text.toString()
        var password = binding.password.text.toString()
        var passwordConfirm = binding.passwordConfirm.text.toString()
        var idHome = binding.homeId.text.toString()
        if (name == null || name == "") {
            binding.name.error = "Masukkan nama terlebih dahulu"
        } else {
            if (email == null || email == "" ) {
                binding.username.error = "Masukkan email terlebih dahulu"
            } else {
                if(!isValidEmailId(email)) {
                    binding.username.error = "Email tidak valid"
                } else {
                    if (password == null || password == "") {
                        binding.password.error = "Masukkan Password terlebih dahulu"
                    } else {
                        if (passwordConfirm == null || passwordConfirm == "") {
                            binding.passwordConfirm.error = "Masukkan konfirmasi password"
                        } else {
                            if (passwordConfirm != password ) {
                                binding.passwordConfirm.error = "Konfirmasi sandi harus sama"
                            } else {
                                if (idHome == null || idHome == "") {
                                    binding.homeId.error = "Masukkan ID Home terlebih dahulu"
                                } else {
                                    val user = User(name, email, password, idHome)
                                    cekId(user)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun daftar (user: User) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(LoginActivity.TAG, "createUserWithEmail:success")
                    create(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(LoginActivity.TAG, "createUserWithEmail:failure", task.exception)
                    Snackbar.make(binding.root, "Signup Failed, ${task.exception}", Snackbar.LENGTH_LONG).show()
                    loadingSelesai()
                }
            }
    }
    private fun cekId(user: User) {
            val docRef = db.collection("homes")
                .document(user.idHome)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if(document.exists()) {
                            daftar(user)
                        } else {
                            Snackbar.make(binding.root, "ID Home tidak ditemukan. SignUp gagal", Snackbar.LENGTH_LONG).show()
                            loadingSelesai()
                        }
                    } else {
                        Snackbar.make(binding.root, "ID Home tidak ditemukan. SignUp gagal", Snackbar.LENGTH_LONG).show()
                        loadingSelesai()
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(binding.root, "Maaf, terdapat kesalahan. Mungkin koneksi anda buruk", Snackbar.LENGTH_LONG).show()
                    loadingSelesai()
                }


    }
    private fun create (user : User) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(GalleryFragment.TAG, "DocumentSnapshot added with ID: ")
                showDialog("Signup Berhasil")
            }
            .addOnFailureListener { e ->
                showDialog("Signup gagal")
                Log.w(GalleryFragment.TAG, "Error adding document", e)
                loadingSelesai()

            }    }

    private fun showDialog(pesan : String) {
        val alertDialogBuilder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(
            this
        )
        // set title dialog
        alertDialogBuilder.setTitle(pesan)

        // set pesan dari dialog
        alertDialogBuilder
            .setMessage("Terimakasih, Anda sudah bisa masuk")
            .setCancelable(false)
            .setPositiveButton(
                "Ya",
                DialogInterface.OnClickListener { dialog, id ->
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                })
            .setNegativeButton(
                "Tidak",
                DialogInterface.OnClickListener { dialog, id -> // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    dialog.cancel()
                })

        // membuat alert dialog dari builder
        val alertDialog: AlertDialog = alertDialogBuilder.create()

        // menampilkan alert dialog
        alertDialog.show()
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