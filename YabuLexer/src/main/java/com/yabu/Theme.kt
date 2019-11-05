package com.yabu

/**
 * The base theme class for all Yabu themes.
 */
interface Theme {

    /**
     * Map token name to theme value.
     * @param name
     */
    fun mapTokenTheme(name: String): String
}