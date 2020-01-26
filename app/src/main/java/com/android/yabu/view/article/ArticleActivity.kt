package com.android.yabu.view.article

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.android.yabu.databinding.ActivityArticleBinding
import com.android.yabu.model.feed.model.Article
import com.android.yabu.view.ResourceBoundUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yabu.Lexer
import com.yabu.TokenTextEffect
import com.yabu.YabuGrammar
import com.yabu.YabuTheme

/**
 * Displays an Article detail page.
 * Consumes an [Article] intent extra and is bound as a [ResourceBoundUI].
 */
class ArticleActivity : AppCompatActivity(), ResourceBoundUI<Article> {
    private lateinit var binding: ActivityArticleBinding

    companion object {
        const val ARTICLE_INTENT_EXTRA = "funky.ARTICLE_INTENT_EXTRA"
    }

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
        Glide.with(this)
            .load(data.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.articleImage)

        binding.articleTitle.text = data.title

        tokeniseText(data.body)
    }

    /**
     * Tokenise the [Article] body to set a spannable string.
     * @param text
     * @see Lexer
     */
    private fun tokeniseText(text: String) {
        // TODO replace for user preference theme.
        val theme = YabuTheme.getTheme(YabuTheme.ThemeId.YABU_LIGHT_THEME)

        // use the default japanese grammar,
        val tokens = Lexer().tokenise(text, YabuGrammar.createJapaneseGrammar())

        val spannable = SpannableString(text)

        tokens.forEachIndexed { _, token ->
            val tokenTheme = theme.mapTokenTheme(token.name)

            // match the effects to the span,
            when (tokenTheme.effect) {
                TokenTextEffect.CLICKABLE ->
                    spannable.setSpan(YabuTokenClickableSpan(this, token),
                        token.startIndex,
                        token.startIndex + token.value.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                TokenTextEffect.BOLD -> {}

                TokenTextEffect.NONE -> {}
            }

            spannable.setSpan(ForegroundColorSpan(Color.parseColor(tokenTheme.color)),
                token.startIndex,
                token.startIndex + token.value.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.articleBody.apply {
            this.text = spannable
            this.linksClickable = true
            this.isClickable = true
            this.movementMethod = LinkMovementMethod.getInstance()
        }
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
