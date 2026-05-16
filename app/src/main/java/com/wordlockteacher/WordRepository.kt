package com.wordlockteacher

import android.content.Context
import com.wordlockteacher.data.AppDatabase
import com.wordlockteacher.data.WordDao
import com.wordlockteacher.data.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WordRepository {
    private lateinit var wordDao: WordDao

    fun initialize(context: Context) {
        if (!::wordDao.isInitialized) {
            wordDao = AppDatabase.getDatabase(context.applicationContext).wordDao()
        }
    }

    private val defaultWords = listOf(
        WordEntity(word="नमस्ते", languageCode="SANS", meaning="A respectful greeting / I bow to you", meaningLanguageCode="ENG", pronunciation="nuh-muh-steh", isCustom=false),
        WordEntity(word="शांति", languageCode="SANS", meaning="Peace / Tranquility", meaningLanguageCode="ENG", pronunciation="shaan-tee", isCustom=false),
        WordEntity(word="गुरु", languageCode="SANS", meaning="Teacher / Spiritual guide", meaningLanguageCode="ENG", pronunciation="goo-roo", isCustom=false),
        WordEntity(word="ध्यान", languageCode="SANS", meaning="Meditation / Deep contemplation", meaningLanguageCode="ENG", pronunciation="dhyaa-n", isCustom=false),
        WordEntity(word="मोक्ष", languageCode="SANS", meaning="Liberation / Release from cycle of rebirth", meaningLanguageCode="ENG", pronunciation="mok-sh", isCustom=false),
        WordEntity(word="कर्म", languageCode="SANS", meaning="Action / Deed / Law of cause and effect", meaningLanguageCode="ENG", pronunciation="kur-m", isCustom=false),
        WordEntity(word="अहिंसा", languageCode="SANS", meaning="Non-violence", meaningLanguageCode="ENG", pronunciation="uh-hin-saa", isCustom=false),
        WordEntity(word="सत्य", languageCode="SANS", meaning="Truth", meaningLanguageCode="ENG", pronunciation="sut-yuh", isCustom=false),
        WordEntity(word="धन्यवाद", languageCode="NEP", meaning="Thank you", meaningLanguageCode="ENG", pronunciation="dhun-yuh-baad", isCustom=false),
        WordEntity(word="माया", languageCode="NEP", meaning="Love / Affection", meaningLanguageCode="ENG", pronunciation="maa-yaa", isCustom=false),
        WordEntity(word="साथी", languageCode="NEP", meaning="Friend / Companion", meaningLanguageCode="ENG", pronunciation="saa-thee", isCustom=false),
        WordEntity(word="खुसी", languageCode="NEP", meaning="Happiness / Joy", meaningLanguageCode="ENG", pronunciation="khoo-see", isCustom=false),
        WordEntity(word="पानी", languageCode="NEP", meaning="Water", meaningLanguageCode="ENG", pronunciation="paa-nee", isCustom=false),
        WordEntity(word="आकाश", languageCode="NEP", meaning="Sky", meaningLanguageCode="ENG", pronunciation="aa-kaash", isCustom=false),
        WordEntity(word="फूल", languageCode="NEP", meaning="Flower", meaningLanguageCode="ENG", pronunciation="phool", isCustom=false),
        WordEntity(word="घर", languageCode="NEP", meaning="Home", meaningLanguageCode="ENG", pronunciation="ghar", isCustom=false),
        WordEntity(word="Serendipity", languageCode="ENG", meaning="Finding something good without looking for it", meaningLanguageCode="ENG", pronunciation="seh-ren-dip-ih-tee", isCustom=false),
        WordEntity(word="Ephemeral", languageCode="ENG", meaning="Lasting for a very short time", meaningLanguageCode="ENG", pronunciation="eh-fem-er-ul", isCustom=false),
        WordEntity(word="Resilience", languageCode="ENG", meaning="Ability to recover quickly from difficulties", meaningLanguageCode="ENG", pronunciation="rih-zil-yuns", isCustom=false),
        WordEntity(word="Eloquent", languageCode="ENG", meaning="Fluent and persuasive in speaking or writing", meaningLanguageCode="ENG", pronunciation="el-oh-kwent", isCustom=false),
        WordEntity(word="Luminous", languageCode="ENG", meaning="Full of or shedding light; bright", meaningLanguageCode="ENG", pronunciation="loo-mih-nus", isCustom=false),
        WordEntity(word="Solitude", languageCode="ENG", meaning="State of being alone", meaningLanguageCode="ENG", pronunciation="sol-ih-tood", isCustom=false)
    )

    suspend fun seedDatabaseIfEmpty() {
        withContext(Dispatchers.IO) {
            if (wordDao.getWordCount() == 0) {
                defaultWords.forEach { wordDao.insertWord(it) }
            }
        }
    }

    suspend fun getDailyWords(
        selectedLanguages: List<String> = listOf("SANS", "NEP", "ENG"),
        countPerLanguage: Int = 1
    ): List<WordEntry> = withContext(Dispatchers.IO) {
        val result = mutableListOf<WordEntry>()
        selectedLanguages.forEach { langCode ->
            val words = wordDao.getRandomWordsByLanguage(langCode, countPerLanguage)
            result.addAll(words.map { it.toDomainModel() })
        }
        result.shuffled()
    }

    suspend fun addCustomWord(word: WordEntry) {
        val entity = WordEntity(
            word = word.word,
            languageCode = word.languageCode,
            meaning = word.meaning,
            meaningLanguageCode = word.meaningLanguageCode,
            pronunciation = word.pronunciation,
            exampleSentence = word.exampleSentence,
            difficulty = word.difficulty,
            isCustom = true
        )
        wordDao.insertWord(entity)
    }

    suspend fun getAllCustomWords(): List<WordEntry> {
        return wordDao.getAllCustomWords().map { it.toDomainModel() }
    }

    private fun WordEntity.toDomainModel(): WordEntry {
        return WordEntry(
            id = this.id,
            word = this.word,
            languageCode = this.languageCode,
            meaning = this.meaning,
            meaningLanguageCode = this.meaningLanguageCode,
            pronunciation = this.pronunciation,
            exampleSentence = this.exampleSentence,
            difficulty = this.difficulty
        )
    }
}
