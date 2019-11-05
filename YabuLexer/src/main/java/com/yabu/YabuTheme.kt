package com.yabu

object YabuThemes {

    object ThemeId {
        const val YABU_LIGHT_THEME = "yabu-light"
    }

    /**
     * Get theme from id.
     * @param id [String]
     */
    fun getTheme(id: String): Theme {
        return when (id) {

            ThemeId.YABU_LIGHT_THEME -> YabuLight

            else -> YabuLight
        }
    }

    /**
     * The default Yabu Light Theme.
     * Inherits [Theme].
     */
    object YabuLight : Theme {

        override fun mapTokenTheme(name: String): String {
            // match the token name to the theme color...
            return when (name) {

                YabuGrammar.TokenNames.NUMBER -> "#00ff00"

                YabuGrammar.TokenNames.PUNCTUATION -> "#00ff00"

                YabuGrammar.TokenNames.PARTICLE -> "#00ff00"

                YabuGrammar.TokenNames.LATIN -> "#00ff00"

                else -> "#00ff00"
            }
        }
    }
}