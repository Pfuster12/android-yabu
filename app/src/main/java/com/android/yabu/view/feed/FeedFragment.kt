package com.android.yabu.view.feed

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.yabu.databinding.FragmentFeedBinding
import com.android.yabu.model.Resource
import com.android.yabu.model.Status
import com.android.yabu.model.feed.model.Feed
import com.android.yabu.view.ResourceBoundUI
import com.android.yabu.view.article.ArticleActivity
import com.android.yabu.utils.LogUtils
import com.android.yabu.viewmodel.feed.FeedViewModel
import com.android.yabu.viewmodel.feed.FeedViewModelFactoryProvider

/**
 * Displays the Feed of articles to open and read.
 * A [Fragment] subclass of the main Activity viewpager.
 * A [ResourceBoundUI] observing a [Feed].
 */
class FeedFragment : Fragment(), ResourceBoundUI<Feed> {

    private lateinit var binding: FragmentFeedBinding

    /**
     * [onCreateView] override.
     */
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
        val model = ViewModelProviders.of(this,
            FeedViewModelFactoryProvider.create(context))[FeedViewModel::class.java]

        model.feed.observe(this, Observer<Resource<Feed>> { resource ->
            LogUtils.debug("Feed resource status changed: ${resource.status}")

            when (resource.status) {
                Status.LOADING -> loading()

                Status.SUCCESS -> {
                    if (resource.data != null && resource.data.articles.isNotEmpty()) {
                        LogUtils.debug("Success getting feed timestamped at: ${resource.data.timestamp.generateTimestamp()}")

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
            // send to article detail screen on click event,
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra(ArticleActivity.ARTICLE_INTENT_EXTRA, data.articles[pos])
            startActivity(intent)
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
