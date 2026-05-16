package com.wordlockteacher.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val languageCode: String,
    val meaning: String,
    val meaningLanguageCode: String,
    val pronunciation: String?,
    val exampleSentence: String?,
    val difficulty: Int = 1,
    val isCustom: Boolean = false
)
