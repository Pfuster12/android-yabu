package com.android.yabu.repositories.feed

/**
 * A Wikipedia article.
 */
data class Article(val id: Long,
              val title: String,
              val body: String)