package com.android.yabu.view.main

import android.content.Context
import android.graphics.drawable.Icon
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.android.yabu.R
import kotlinx.android.synthetic.main.tab.view.*

/**
 * [FrameLayout] displaying a tab (icon + text).
 * Child of a TabLayout.
 */
class Tab(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    /**
     * [Tab] root view.
     */
    private var inflatedView = View(context)

    /**
     * [Tab] text.
     */
    private var tabText = "Tab"

    /**
     * [Tab] icon. Optional.
     */
    private var iconRes: Int? = R.drawable.ic_share

    // inflate the tab layout...
    init {
        inflatedView = LayoutInflater.from(context).inflate(R.layout.tab, this, false)

        // get attrs,
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Tab,
            0, 0).apply {

            try {
                tabText = getString(R.styleable.Tab_tabText) ?: ""
                iconRes = getResourceId(R.styleable.Tab_tabIcon, R.drawable.ic_share)
            } finally {
                recycle()
            }
        }

        inflatedView.tab_text.text = tabText
        if (iconRes != null) {
            inflatedView.tab_icon.setImageIcon(Icon.createWithResource(context, iconRes as Int))
        }

        this.addView(inflatedView)
    }
}