package com.android.yabu.ui.words

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.yabu.databinding.FragmentWordsBinding

/**
 * A simple [Fragment] subclass.
 */
class WordsFragment : Fragment() {

    private lateinit var binding: FragmentWordsBinding

    /**
     * [onCreateView] override.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentWordsBinding.inflate(inflater)
        return binding.root
    }
}
