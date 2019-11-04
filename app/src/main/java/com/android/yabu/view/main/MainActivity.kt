package com.android.yabu.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.yabu.R
import com.android.yabu.databinding.ActivityMainBinding
import com.android.yabu.view.feed.FeedFragment
import com.android.yabu.view.words.WordsFragment

/**
 * App Launch [AppCompatActivity]. Handles the display of the main tabs in the App.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * [onCreate] override.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViewPager()
    }

    /**
     * Bind [TabLayout] to the Fragment container.
     */
    private fun bindViewPager() {
        binding.mainTabLayout.setupOnTabClick(object : TabLayout.OnTabClick {
            override fun onTabClick(pos: Int) {
                val fragment = when (pos) {
                    0 -> WordsFragment()
                    1 -> FeedFragment()
                    2 -> WordsFragment()
                    else -> WordsFragment()
                }

                replaceFragment(fragment)
            }
        })
    }

    /**
     * Replaces the viewpager fragment.
     * @param fragment
     */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}
