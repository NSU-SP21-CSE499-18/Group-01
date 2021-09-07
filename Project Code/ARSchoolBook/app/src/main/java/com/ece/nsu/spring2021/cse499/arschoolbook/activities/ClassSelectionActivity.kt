package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.adpters.SelectClassAdapter

lateinit var data: Array<String>
class ClassSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_selection)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view_class)
        recyclerview.layoutManager = LinearLayoutManager(this)

        data = resources.getStringArray(R.array.class_names)
        val adapter = SelectClassAdapter(data, this)
        recyclerview.adapter = adapter
    }
}