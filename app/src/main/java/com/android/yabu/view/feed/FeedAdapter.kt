package com.android.yabu.view.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.yabu.R
import com.android.yabu.model.feed.model.Feed
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * [RecyclerView.Adapter] for the [FeedFragment] list.
 * @param context
 * @param feed
 */
class FeedAdapter(private val context: Context?,
                  private val feed: Feed,
                  private val itemClick: (pos: Int) -> Unit)
    : RecyclerView.Adapter<FeedItemViewHolder>() {

    companion object {
        const val ITEM_TYPE = 101

        const val FOOTER_TYPE = 102
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        return when (viewType) {
            ITEM_TYPE -> {
                FeedItemViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.feed_item,
                        parent,
                        false), itemClick)
            }

            FOOTER_TYPE -> {
                FeedItemViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.feed_item,
                        parent,
                        false)) {}
            }

            else -> {
                FeedItemViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.feed_item,
                        parent,
                        false), itemClick)
            }
        }
    }

    override fun getItemCount() = feed.articles.size

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val item = feed.articles[position]

        holder.feedTitle?.text = item.title
        holder.feedBody?.text = item.body

        if (context != null && holder.feedImage != null) {
            Glide.with(context)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.feedImage as ImageView)
        }
    }

    override fun getItemViewType(position: Int) =  when (position) {
        in feed.articles.indices -> ITEM_TYPE

        feed.articles.size -> FOOTER_TYPE

        else -> ITEM_TYPE
    }
}

class FeedItemViewHolder(view: View, itemClick: (pos: Int) -> Unit) : RecyclerView.ViewHolder(view) {

    var feedImage: ImageView? = null
    var feedTitle: TextView? = null
    var feedBody: TextView? = null

    init {
        feedImage = view.findViewById(R.id.feed_item_image)
        feedTitle = view.findViewById(R.id.feed_item_title)
        feedBody = view.findViewById(R.id.feed_item_body)

        view.setOnClickListener { itemClick.invoke(adapterPosition) }
    }
}