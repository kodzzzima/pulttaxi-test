package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.util.UserPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserPreferences.getPreference(this)
    }
}