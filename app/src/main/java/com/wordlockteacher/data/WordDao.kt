package com.wordlockteacher.data

import androidx.room.*

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE languageCode = :langCode ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomWordsByLanguage(langCode: String, limit: Int): List<WordEntity>

    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomWords(limit: Int): List<WordEntity>

    @Insert
    suspend fun insertWord(word: WordEntity): Long

    @Delete
    suspend fun deleteWord(word: WordEntity)

    @Query("SELECT * FROM words WHERE isCustom = 1")
    suspend fun getAllCustomWords(): List<WordEntity>

    @Query("SELECT COUNT(*) FROM words")
    suspend fun getWordCount(): Int
}
