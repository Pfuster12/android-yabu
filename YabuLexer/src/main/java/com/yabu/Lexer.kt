package com.yabu

/**
 * A lexer serves to tokenise Japanese character based on defined grammar rules.
 */
class Lexer {

    /**
     * Tokenise the text.
     * @param text
     */
    fun tokenise(text: String, grammar: YabuGrammar): List<Token> {
        val tokens = mutableListOf<Token>()

        applyGrammars(text, grammar, tokens)

        // sort the tokens by start index...
        tokens.sortBy { token -> token.startIndex }

        // reduce tokens with identical start index,
        return reduceTokens(tokens)
    }

    /**
     * Applies the grammar rules to the text.
     * @param text
     * @param langGrammar
     * @param tokens
     */
    private fun applyGrammars(text: String, langGrammar: YabuGrammar, tokens: MutableList<Token>) {
        langGrammar.grammars.forEach { grammar ->
            val regex = Regex(grammar.rule, RegexOption.MULTILINE)
            val matches = regex.findAll(text)

            // map matches to tokens and add to list...
            matches.forEach { match ->
                tokens.add(Token(name = grammar.name,
                    value = match.value,
                    startIndex = match.range.first))
            }
        }
    }

    /**
     * Reduces tokens with identical start index.
     * @param tokens [Token] [MutableList] to reduce.
     */
    private fun reduceTokens(tokens: MutableList<Token>): MutableList<Token> {
        val reducedTokens = mutableListOf<Token>()

        tokens.reduce { acc, token ->
            if (token.startIndex != acc.startIndex) {
                reducedTokens.add(acc)
            }

            return@reduce token
        }

        return reducedTokens
    }
}
