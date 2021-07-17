package com.ece.nsu.spring2021.cse499.arschoolbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val backButton : ImageButton = findViewById(R.id.backBtn)
        val chapterNoTv: TextView = findViewById(R.id.selected_ch_no)
        val chapterNameTv: TextView = findViewById(R.id.selected_ch_name)
        val bundle: Bundle? = intent.extras
        val chapterNo: String = bundle?.get("Chapter-No").toString()
        val chapterName : String = bundle?.get("Chapter-Name").toString()

        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName

        backButton.setOnClickListener { finish() }

    }
}