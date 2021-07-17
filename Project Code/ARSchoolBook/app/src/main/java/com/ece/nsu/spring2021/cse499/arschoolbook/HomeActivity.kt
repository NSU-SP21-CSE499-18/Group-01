package com.ece.nsu.spring2021.cse499.arschoolbook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import belka.us.androidtoggleswitch.widgets.ToggleSwitch

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toggleSwitch : ToggleSwitch = findViewById(R.id.lng_switch)

        toggleSwitch.setOnToggleSwitchChangeListener { position, isChecked ->
            Toast.makeText(this,position.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}