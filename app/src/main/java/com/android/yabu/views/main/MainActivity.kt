package com.android.yabu.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.yabu.R
import com.android.yabu.databinding.ActivityMainBinding

/**
 * App Launch [AppCompatActivity]. Handles the display of the main tabs in the App.
 */
class MainActivity : AppCompatActivity() {

    /**
     * ViewBinding.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * [onCreate] override.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
