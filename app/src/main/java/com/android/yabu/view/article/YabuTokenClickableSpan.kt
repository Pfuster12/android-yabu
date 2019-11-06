package com.android.yabu.view.article

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast

class YabuTokenClickableSpan(val context: Context) : ClickableSpan() {

    override fun onClick(widget: View) {
        Toast.makeText(context,
            widget.x.toString(),
            Toast.LENGTH_SHORT)
            .show()
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
    }
}