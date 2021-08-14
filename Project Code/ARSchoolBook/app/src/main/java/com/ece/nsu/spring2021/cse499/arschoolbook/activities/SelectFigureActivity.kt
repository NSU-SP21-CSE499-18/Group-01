package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.adpters.SelectFigureAdapter

class SelectFigureActivity : AppCompatActivity() {

    private var chapterNo: String = ""
    private var chapterName: String = ""
    private lateinit var SELECTED_CHAPTER: String
    lateinit var data: Array<String>

    //UI
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView
    private lateinit var recyclerview: RecyclerView

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
        SELECTED_CHAPTER = chapterNo

        //Init UI components
        chapterNoTv = findViewById(R.id.selected_ch_no_sf)
        chapterNameTv = findViewById(R.id.selected_ch_name_sf)
        recyclerview = findViewById<RecyclerView>(R.id.recycler_view)


        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName

        //Recycler View
        recyclerview.layoutManager = LinearLayoutManager(this)
        data = getData(SELECTED_CHAPTER)
        val adapter = SelectFigureAdapter(data, this)
        recyclerview.adapter = adapter

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

    fun getData(chapterNo: String): Array<String> {

        when (chapterNo) {
            "Chapter 1" -> {
                return resources.getStringArray(R.array.fig_ch_1)
            }
            "Chapter 2" -> {
                return resources.getStringArray(R.array.fig_ch_2)
            }
            "Chapter 3" -> {
                return resources.getStringArray(R.array.fig_ch_3)
            }
            "Chapter 4" -> {
                return resources.getStringArray(R.array.fig_ch_4)
            }
            "Chapter 5" -> {
                return resources.getStringArray(R.array.fig_ch_5)
            }
            "Chapter 6" -> {
                return resources.getStringArray(R.array.fig_ch_6)
            }
            "Chapter 7" -> {
                return resources.getStringArray(R.array.fig_ch_7)
            }
            "Chapter 8" -> {
                return resources.getStringArray(R.array.fig_ch_8)
            }
            "Chapter 9" -> {
                return resources.getStringArray(R.array.fig_ch_9)
            }
            "Chapter 10" -> {
                return resources.getStringArray(R.array.fig_ch_10)
            }
            "Chapter 11" -> {
                return resources.getStringArray(R.array.fig_ch_11)
            }
            "Chapter 12" -> {
                return resources.getStringArray(R.array.fig_ch_12)
            }
            "Chapter 13" -> {
                return resources.getStringArray(R.array.fig_ch_13)
            }
            "Chapter 14" -> {
                return resources.getStringArray(R.array.fig_ch_14)
            }
            else -> return emptyArray()
        }
    }

}