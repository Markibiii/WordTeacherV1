package com.wordlockteacher

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wordlockteacher.databinding.ActivityLockScreenBinding
import kotlinx.coroutines.launch

class LockScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockScreenBinding
    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        binding = ActivityLockScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WordRepository.initialize(this)

        setupRecyclerView()
        loadWords()

        binding.btnDismiss.setOnClickListener {
            finish()
        }

        binding.btnNextSet.setOnClickListener {
            loadWords()
        }
    }

    private fun setupRecyclerView() {
        wordAdapter = WordAdapter()
        binding.recyclerWords.apply {
            layoutManager = LinearLayoutManager(this@LockScreenActivity)
            adapter = wordAdapter
        }
    }

    private fun loadWords() {
        lifecycleScope.launch {
            val dailyWords = WordRepository.getDailyWords(
                selectedLanguages = listOf("SANS", "NEP", "ENG"),
                countPerLanguage = 1
            )
            wordAdapter.submitList(dailyWords)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
