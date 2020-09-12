package com.aliraza.mad_final_exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBtn.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
        val user =
        sellBtn.setOnClickListener {
            if(FirebaseAuth.getInstance().uid!=null){
                startActivity(Intent(this,SellActivity::class.java))
            }else{
                startActivity(Intent(this,AuthActivity::class.java))
            }

        }

    }
}