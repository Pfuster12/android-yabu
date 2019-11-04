package com.android.yabu.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.yabu.databinding.ActivityArticleBinding
import com.android.yabu.repositories.feed.Article
import com.android.yabu.ui.ResourceBoundUI

/**
 * Displays an Article detail page.
 * Consumes an [Article] intent extra and is bound as a [ResourceBoundUI].
 */
class ArticleActivity : AppCompatActivity(), ResourceBoundUI<Article> {

    companion object {
        const val ARTICLE_INTENT_EXTRA = "funky.ARTICLE_INTENT_EXTRA"
    }

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
    }

    override fun observeData() {
        // in this case we observe the intent data,
        val article = intent.getSerializableExtra(ARTICLE_INTENT_EXTRA) as Article
        bindViewModel(article)
    }

    override fun bindViewModel(data: Article) {
        binding.articleTitle.text = data.title
        binding.articleBody.text = data.body
    }

    override fun loading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun idle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun empty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
