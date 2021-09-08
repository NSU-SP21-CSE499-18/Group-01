package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R


class GeHomeActivity : AppCompatActivity() {
    lateinit var chapterNumbers: Array<String>
    lateinit var chapterNames: Array<String>
    lateinit var SELECTED_CLASS: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ge_home)

        SELECTED_CLASS = intent.getStringExtra("SelectedClass").toString()
        chapterNames = getChapterNamesFromSelectedClass(SELECTED_CLASS)
        chapterNumbers = resources.getStringArray(R.array.chapter_numbers)

        //val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        //recyclerview.layoutManager = LinearLayoutManager(this)
    }

    private fun getChapterNamesFromSelectedClass(className: String): Array<String> {

        return when (className) {
            "Class 7" -> {
                resources.getStringArray(R.array.chapter_names_c7)
            }
            else -> emptyArray()
        }
    }

}