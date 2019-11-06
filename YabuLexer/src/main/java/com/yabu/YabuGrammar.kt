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

    object TokenName {

        const val NUMBER = "number"

        const val LATIN = "latin"

        const val WHITESPACE = "whitespace"

        const val PARTICLE = "particle"

        const val PUNCTUATION = "punctuation"

        const val HIRAGANA = "hiragana"

        const val KATAKANA = "katakana"

        const val KANJI = "kanji"
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
                Grammar(TokenName.NUMBER, "\\d"),
                Grammar(TokenName.LATIN, "[a-zA-Z]"),
                Grammar(TokenName.WHITESPACE, "\\s"),
                Grammar(TokenName.PARTICLE, "の|を|へ|は|と"),
                Grammar(TokenName.PUNCTUATION, "\\W"),
                // using the \p{script=} regex notation,
                Grammar(TokenName.HIRAGANA, "\\p{script=Hiragana}"),
                Grammar(TokenName.KATAKANA, "\\p{script=Katakana}"),
                Grammar(TokenName.KANJI, "\\p{script=Han}")
            )
        }
    }
}