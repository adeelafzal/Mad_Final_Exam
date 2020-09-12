package com.aliraza.mad_final_exam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(),MyInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val myList =
            intent.getSerializableExtra("mylist") as ArrayList<Vehicle>

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myRecyclerView.layoutManager = linearLayoutManager
        myRecyclerView.adapter = SearchAdapter(myList)
    }

    override var mylist: ArrayList<Vehicle>
        get() = TODO("Not yet implemented")
        set(value) {}
}