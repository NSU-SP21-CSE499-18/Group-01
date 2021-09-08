package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import belka.us.androidtoggleswitch.widgets.ToggleSwitch
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.adpters.HomeAdapter
import java.util.*


class GeHomeActivity : AppCompatActivity() {
    lateinit var chapterNumbers: Array<String>
    lateinit var chapterNames: Array<String>
    lateinit var SELECTED_CLASS: String
    lateinit var classNameTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ge_home)
        val toggleSwitch : ToggleSwitch = findViewById(R.id.lng_switch_ge)
        val bundle: Bundle? = intent.extras
        val language = bundle?.get("lng")

        if(language!=null)
        { if(language == "bn")
            { toggleSwitch.checkedTogglePosition = 1 }
            else
            { toggleSwitch.checkedTogglePosition = 0 }
        }


        toggleSwitch.setOnToggleSwitchChangeListener { position, isChecked ->
            if(position==1)
            {
                setLang("bn")
            }
            else {
                setLang("en")
            }
        }

        init()
    }

    fun init()
    {
        classNameTV = findViewById(R.id.className_home)
        SELECTED_CLASS = intent.getStringExtra("SelectedClass").toString()
        setClassNameTitle(SELECTED_CLASS)
        chapterNames = getChapterNamesFromSelectedClass(SELECTED_CLASS)
        chapterNumbers = resources.getStringArray(R.array.chapter_numbers)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view_chapters)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = HomeAdapter(chapterNumbers,chapterNames,this)
        recyclerview.adapter = adapter
    }
    private fun getChapterNamesFromSelectedClass(className: String): Array<String> {

        return when (className) {
            "Class 7" -> {
                resources.getStringArray(R.array.chapter_names_c7)
            }
            else -> emptyArray()
        }
    }

    fun setClassNameTitle(className: String)
    {
        if (className=="Class 6" || className=="ষষ্ঠ শ্রেণী")
        {
            classNameTV.text = resources.getString(R.string.cl_6).toString()
        }
        else if(className=="Class 7" || className=="সপ্তম শ্রেণি")
        {
            classNameTV.text = resources.getString(R.string.cl_7).toString()
        }
        else if(className=="Class 8" || className=="অষ্ঠম শ্রেণ")
        {
            classNameTV.text = resources.getString(R.string.cl_8).toString()
        }
        else if(className=="Class 9" || className=="নবম শ্রেণী")
        {
            classNameTV.text = resources.getString(R.string.cl_9).toString()
        }
        else
        {
            classNameTV.text = resources.getString(R.string.cl_10).toString()
        }
    }
    private fun setLocale(activity: Activity, languageCode: String? ) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

    private fun setLang(lang: String?)
    {
        setLocale(this,lang)
        val intent = Intent(this, GeHomeActivity::class.java)
        intent.putExtra("lng",lang)
        intent.putExtra("SelectedClass",SELECTED_CLASS)
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

}