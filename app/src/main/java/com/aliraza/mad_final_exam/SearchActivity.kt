package com.aliraza.mad_final_exam

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    val searchList = ArrayList<Vehicle>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val progressDialog = ProgressDialog(this@SearchActivity)
        progressDialog.setTitle("Searching")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        submitBtn.setOnClickListener {
            progressDialog.show()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Sells")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            if (snapshot.child("make").getValue().toString()!!
                                    .equals(makeText.text.toString()) &&
                                snapshot.child("model").getValue().toString()!!
                                    .equals(modelText.text.toString()) &&
                                snapshot.child("mileage").getValue().toString()!!
                                    .equals(mileageText.text.toString()) &&
                                snapshot.child("color").getValue().toString()!!
                                    .equals(colorText.text.toString()) &&
                                snapshot.child("city").getValue().toString()!!
                                    .equals(cityText.text.toString())
                            ) {
                                searchList.add(
                                    Vehicle(
                                        snapshot.child("city").getValue().toString(),
                                        snapshot.child("color").getValue().toString(),
                                        snapshot.child("description").getValue().toString(),
                                        snapshot.child("images").getValue().toString(),
                                        snapshot.child("make").getValue().toString(),
                                        snapshot.child("mileage").getValue().toString(),
                                        snapshot.child("model").getValue().toString(),
                                        snapshot.child("sname").getValue().toString()
                                    )
                                )
                            }
                        }

                        if (searchList.size > 0) {
                            progressDialog.cancel()

                            val intent = Intent(this@SearchActivity, ResultActivity::class.java)
                            startActivity(intent)
                        } else {
                            progressDialog.cancel()
                            Toast.makeText(
                                this@SearchActivity,
                                "Data does not exist",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    } else {
                        progressDialog.cancel()
                        Toast.makeText(
                            this@SearchActivity,
                            "Data does not exist",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressDialog.cancel()
                    Toast.makeText(this@SearchActivity, "" + error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })

        }

    }


}
