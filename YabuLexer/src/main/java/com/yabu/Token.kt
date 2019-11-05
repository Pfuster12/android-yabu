package com.yabu

/**
 * A [Token] is an identifiable set of characters matched by a grammar rule.
 * In this case it represents a distinct Japanese word extracted from a Japanese body of text.
 * @property name [String] Name of the Japanese grammars defined in the lexer.
 * @property value of this token.
 * @property startIndex of this token.
 */
data class Token(var name: String = "",
                 var value: String = "",
                 var startIndex: Int = 0)