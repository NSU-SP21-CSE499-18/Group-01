package com.ece.nsu.spring2021.cse499.arschoolbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ece.nsu.spring2021.cse499.arschoolbook.R;

/**
 * Activity to show user selected AR model
 */
public class ViewSelectedArModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_ar_model);
    }
}