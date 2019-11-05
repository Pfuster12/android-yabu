package com.yabu

/**
 * This is a language grammar for Japanese that defines a
 * set of RegEx rules of the language using [Grammar]'s.
 * @property lang_id
 * @property grammars
 * @property regexFlags
 */
class YabuGrammar private constructor(val lang_id: Int,
                       val grammars: List<Grammar>,
                       val regexFlags: String = "") {

    companion object {

        fun createJapaneseGrammar(): YabuGrammar {
            return YabuGrammar(LangId.JAPANESE,
                GrammarBuilder.createDefaultGrammar())
        }
    }

    object TokenNames {

        const val NUMBER = "number"

        const val LATIN = "latin"

        const val WHITESPACE = "whitespace"

        const val PARTICLE = "particle"

        const val PUNCTUATION = "punctuation"
    }

    /**
     * Grammar builder object for different lang ids.
     */
    object GrammarBuilder {

        /**
         * Helper to create [LangId.JAPANESE] (Japanese) grammar.
         */
        fun createDefaultGrammar(): List<Grammar> {
            return listOf(
                Grammar(TokenNames.NUMBER, "\\d"),
                Grammar(TokenNames.LATIN, "[a-zA-Z]"),
                Grammar(TokenNames.WHITESPACE, "\\s"),
                Grammar(TokenNames.PARTICLE, "の|を|へ|は|と"),
                Grammar(TokenNames.PUNCTUATION, "。|_|-")
            )
        }
    }
}