package com.android.yabu.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.yabu.databinding.FragmentFeedBinding
import com.android.yabu.repositories.Resource
import com.android.yabu.repositories.Status
import com.android.yabu.repositories.feed.Feed
import com.android.yabu.ui.ResourceBoundUI
import com.android.yabu.ui.main.MainActivity
import com.android.yabu.utils.LogUtils
import com.android.yabu.viewmodels.feed.FeedViewModel
import javax.inject.Inject

/**
 * Displays the Feed of articles to open and read.
 * A [Fragment] of the main viewpager.
 * A [ResourceBoundUI] observes a [Feed].
 */
class FeedFragment : Fragment(), ResourceBoundUI<Feed> {

    private lateinit var binding: FragmentFeedBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * [onCreateView] override.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater)

        // observe the feed data,
        observeData()

        return binding.root
    }

    /**
     * [observeData] override.
     */
    override fun observeData() {
        val model = ViewModelProviders.of(this, viewModelFactory)[FeedViewModel::class.java]

        model.feed.observe(this, Observer<Resource<Feed>> { resource ->
            LogUtils.debug("Feed resource status changed: ${resource.status}")
            when (resource.status) {
                Status.LOADING -> loading()

                Status.SUCCESS -> {
                    if (resource.data != null && resource.data.articles.isNotEmpty()) {
                        LogUtils.debug("Success getting feed: ${resource.data.articles[0]}")
                        bindViewModel(resource.data)
                        idle()
                    } else {
                        empty()
                    }
                }

                Status.ERROR -> error()
            }
        })
    }

    override fun bindViewModel(data: Feed) {
        binding.feedList.adapter = FeedAdapter(context, data) { pos ->
            Toast.makeText(context, "$pos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun loading() {
        binding.feedError.visibility = View.GONE
        binding.feedList.visibility = View.GONE

        binding.feedLoading.visibility = View.VISIBLE
    }

    override fun idle() {
        binding.feedLoading.visibility = View.GONE
        binding.feedError.visibility = View.GONE

        binding.feedList.visibility = View.VISIBLE
    }

    override fun empty() {
        binding.feedLoading.visibility = View.GONE
        binding.feedError.visibility = View.GONE
        binding.feedList.visibility = View.GONE
    }

    override fun error() {
        binding.feedLoading.visibility = View.GONE
        binding.feedList.visibility = View.GONE

        binding.feedError.visibility = View.VISIBLE
    }
}
