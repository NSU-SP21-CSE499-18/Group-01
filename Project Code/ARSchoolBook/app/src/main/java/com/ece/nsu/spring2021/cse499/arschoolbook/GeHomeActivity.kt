package com.ece.nsu.spring2021.cse499.arschoolbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

lateinit var data: Array<String>
    lateinit var SELECTED_CLASS: String
class GeHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ge_home)

        SELECTED_CLASS = intent.getStringExtra("SelectedClass").toString()

        //val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        //recyclerview.layoutManager = LinearLayoutManager(this)
    }
}