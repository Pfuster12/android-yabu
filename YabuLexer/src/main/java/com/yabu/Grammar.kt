package com.yabu

/**
 * A Grammar defines a rule to extract a named token from text.
 * @property name
 * @property rule
 */
data class Grammar(val name: String, val rule: String)