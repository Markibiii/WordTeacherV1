package com.wordlockteacher

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wordlockteacher.databinding.ActivityAddWordBinding
import kotlinx.coroutines.launch

class AddWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WordRepository.initialize(this)

        val languages = SupportedLanguage.values().map { "${it.displayName} (${it.code})" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = adapter

        binding.btnSaveWord.setOnClickListener { saveWord() }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun saveWord() {
        val word = binding.etWord.text.toString().trim()
        val meaning = binding.etMeaning.text.toString().trim()
        val pronunciation = binding.etPronunciation.text.toString().trim().takeIf { it.isNotEmpty() }
        val languageStr = binding.spinnerLanguage.selectedItem.toString()
        val langCode = languageStr.substringAfter("(").substringBefore(")")

        if (word.isEmpty() || meaning.isEmpty()) {
            Toast.makeText(this, "Word and meaning are required", Toast.LENGTH_SHORT).show()
            return
        }

        val newWord = WordEntry(
            id = 0,
            word = word,
            languageCode = langCode,
            meaning = meaning,
            meaningLanguageCode = "ENG",
            pronunciation = pronunciation,
            difficulty = 1
        )

        lifecycleScope.launch {
            WordRepository.addCustomWord(newWord)
            Toast.makeText(this@AddWordActivity, "Word saved to database!", Toast.LENGTH_SHORT).show()
            clearFields()
        }
    }

    private fun clearFields() {
        binding.etWord.text?.clear()
        binding.etMeaning.text?.clear()
        binding.etPronunciation.text?.clear()
    }
}
