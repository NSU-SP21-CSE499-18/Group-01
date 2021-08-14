package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R

class SelectFigureActivity : AppCompatActivity() {

    // Intent
    private var chapterNo: String = ""
    private var chapterName: String = ""

    //UI
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView
    private lateinit var Recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_figure)
        init()
    }

    private fun init()
    {
        // get data for the UI
        val bundle: Bundle? = intent.extras
        chapterNo = bundle?.get("Chapter-No").toString()
        chapterName = bundle?.get("Chapter-Name").toString()

        //Init UI components
        chapterNoTv = findViewById(R.id.selected_ch_no_sf)
        chapterNameTv = findViewById(R.id.selected_ch_name_sf)

        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName
    }

    /**
     * view figures onClick listener
     */
     fun viewFiguresClick(view: View) {
        startActivity(Intent(this, AugmentedImagesActivity::class.java))
    }

    fun backClick(view: View) {
        finish()
        slideInLeftOutRight()
    }

    fun slideInLeftOutRight() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}