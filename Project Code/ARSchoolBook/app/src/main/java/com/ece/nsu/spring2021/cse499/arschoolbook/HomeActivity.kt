package com.ece.nsu.spring2021.cse499.arschoolbook

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import belka.us.androidtoggleswitch.widgets.ToggleSwitch
import java.util.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toggleSwitch : ToggleSwitch = findViewById(R.id.lng_switch)


        val bundle: Bundle? = intent.extras
        val language = bundle?.get("lng")

        if(language!=null)
        {
            if(language.equals("bn"))
            {
                toggleSwitch.checkedTogglePosition = 1
            }
            else
            {
                toggleSwitch.checkedTogglePosition = 0
            }
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
    }


    fun setLocale(activity: Activity, languageCode: String? ) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

    fun setLang(lang: String?)
    {
        setLocale(this,lang)
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("lng",lang)
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}


