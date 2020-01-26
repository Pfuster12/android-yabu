package com.android.yabu.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.yabu.databinding.ActivitySettingsBinding

/**
 * Displays the Settings.
 */
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
