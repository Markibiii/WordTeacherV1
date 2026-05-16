package com.wordlockteacher

data class WordEntry(
    val id: Int,
    val word: String,
    val languageCode: String,
    val meaning: String,
    val meaningLanguageCode: String,
    val pronunciation: String? = null,
    val exampleSentence: String? = null,
    val difficulty: Int = 1
)

enum class SupportedLanguage(val code: String, val displayName: String) {
    SANSKRIT("SANS", "Sanskrit"),
    NEPALI("NEP", "Nepali"),
    ENGLISH("ENG", "English"),
    HINDI("HIN", "Hindi"),
    TIBETAN("TIB", "Tibetan")
}
