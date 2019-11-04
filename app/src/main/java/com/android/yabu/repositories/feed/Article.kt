package com.android.yabu.repositories.feed

import java.io.Serializable

/**
 * A Wikipedia article.
 */
data class Article(val id: Long,
                   val title: String,
                   val body: String,
                   val image: String = ""): Serializable