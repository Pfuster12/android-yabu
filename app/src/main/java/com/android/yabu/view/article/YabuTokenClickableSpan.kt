package com.android.yabu.view.article

import android.content.Context
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import com.yabu.Token

class YabuTokenClickableSpan(val context: Context, private val token: Token) : ClickableSpan() {

    override fun onClick(widget: View) {
        Toast.makeText(context,
            token.value,
            Toast.LENGTH_SHORT)
            .show()
    }
}