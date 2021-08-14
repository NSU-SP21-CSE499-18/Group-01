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

    fun getData(chapterNo: String): Array<String> {

        when (chapterNo) {
            "chapter 1" -> {
                return resources.getStringArray(R.array.fig_ch_1)
            }
            "chapter 2" -> {
                return resources.getStringArray(R.array.fig_ch_2)
            }
            "chapter 3" -> {
                return resources.getStringArray(R.array.fig_ch_3)
            }
            "chapter 4" -> {
                return resources.getStringArray(R.array.fig_ch_4)
            }
            "chapter 5" -> {
                return resources.getStringArray(R.array.fig_ch_5)
            }
            "chapter 6" -> {
                return resources.getStringArray(R.array.fig_ch_6)
            }
            "chapter 7" -> {
                return resources.getStringArray(R.array.fig_ch_7)
            }
            "chapter 8" -> {
                return resources.getStringArray(R.array.fig_ch_8)
            }
            "chapter 9" -> {
                return resources.getStringArray(R.array.fig_ch_9)
            }
            "chapter 10" -> {
                return resources.getStringArray(R.array.fig_ch_10)
            }
            "chapter 11" -> {
                return resources.getStringArray(R.array.fig_ch_11)
            }
            "chapter 12" -> {
                return resources.getStringArray(R.array.fig_ch_12)
            }
            "chapter 13" -> {
                return resources.getStringArray(R.array.fig_ch_13)
            }
            "chapter 14" -> {
                return resources.getStringArray(R.array.fig_ch_14)
            }
            else -> return emptyArray()
        }
    }

}