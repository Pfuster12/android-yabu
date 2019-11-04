package com.android.yabu.view

/**
 * Interface for a UI element bound by a generic resource fetch from a Repository.
 */
interface ResourceBoundUI<T> {
    fun observeData()

    fun bindViewModel(data: T)

    fun loading()

    fun idle()

    fun empty()

    fun error()
}