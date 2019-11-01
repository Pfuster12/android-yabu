package com.android.yabu.views.main

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.children

/**
 * [LinearLayout] subclass displaying a series of Tabs.
 * Coordinates and handles Tab children.
 */
class TabLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * Gets given Tab child.
     * @param i Index of child.
     */
    private fun getTabChild(i: Int): Tab {
        return children.elementAt(i) as Tab
    }

}