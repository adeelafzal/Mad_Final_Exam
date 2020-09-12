package com.aliraza.mad_final_exam

import android.R.attr
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance();
        val progressDialog = ProgressDialog(this@AuthActivity)
        progressDialog.setTitle("Uploading")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)
        SignInBtn.setOnClickListener {
            if (email.text.trim().equals("") || password.text.trim().equals("")) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                mAuth!!.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this@AuthActivity, SellActivity::class.java))
                        } else {
                            progressDialog.cancel()
                            Toast.makeText(
                                this@AuthActivity, "" + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            }
        }

        signUpBtn.setOnClickListener {
            if (email.text.trim().equals("") || password.text.trim().equals("")) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                mAuth!!.createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                )
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            progressDialog.cancel()
                            startActivity(Intent(this@AuthActivity, SellActivity::class.java))
                        } else {
                            progressDialog.cancel()
                            Toast.makeText(
                                this@AuthActivity, "" + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }
}