package com.yabu

object YabuTheme {

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

        override fun mapTokenTheme(name: String): TokenTheme {
            // match the token name to the theme color...
            return when (name) {

                YabuGrammar.TokenName.NUMBER -> TokenTheme("#ff0000")

                YabuGrammar.TokenName.PUNCTUATION -> TokenTheme("#000000")

                YabuGrammar.TokenName.PARTICLE -> TokenTheme("#00ff00", TokenTextEffect.CLICKABLE)

                YabuGrammar.TokenName.LATIN -> TokenTheme("#C589E8")

                YabuGrammar.TokenName.HIRAGANA -> TokenTheme("#0000ff", TokenTextEffect.CLICKABLE)

                YabuGrammar.TokenName.KATAKANA -> TokenTheme("#C589E8", TokenTextEffect.CLICKABLE)

                YabuGrammar.TokenName.KANJI -> TokenTheme("#000000", TokenTextEffect.CLICKABLE)

                else -> TokenTheme("#00ff00")
            }
        }
    }
}

class TokenTheme(val color: String,
                 val effect: TokenTextEffect = TokenTextEffect.NONE)

enum class TokenTextEffect {
    NONE,
    CLICKABLE,
    BOLD
}