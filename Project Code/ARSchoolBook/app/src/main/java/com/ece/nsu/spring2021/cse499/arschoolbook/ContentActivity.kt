package com.ece.nsu.spring2021.cse499.arschoolbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

/**
 * Activity Class for a single chapter on book
 */
class ContentActivity : AppCompatActivity() {

    // ui
    lateinit var backButton : ImageButton
    lateinit var chapterNoTv: TextView
    lateinit var chapterNameTv: TextView

    // models
    var chapterNo: String = ""
    var chapterName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        init()
    }

    private fun init() {

        // initialize UI components
        backButton = findViewById(R.id.backBtn)
        chapterNoTv = findViewById(R.id.selected_ch_no)
        chapterNameTv = findViewById(R.id.selected_ch_name)

        // get data for the UI
        val bundle: Bundle? = intent.extras
        chapterNo = bundle?.get("Chapter-No").toString()
        chapterName = bundle?.get("Chapter-Name").toString()

        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName
    }

    /**
     * back button press listener
     */
    fun backClick(view: View) {
        finish()
    }
}