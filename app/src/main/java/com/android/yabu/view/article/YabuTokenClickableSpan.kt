package com.android.yabu.view.article

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import com.yabu.Token

/**
 * [ClickableSpan] subclass handling a token.
 * @param context
 * @param token
 */
class YabuTokenClickableSpan(val context: Context, val token: Token) : ClickableSpan() {

    override fun onClick(widget: View) {
        Toast.makeText(context,
            token.value,
            Toast.LENGTH_SHORT)
            .show()
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
    }
}