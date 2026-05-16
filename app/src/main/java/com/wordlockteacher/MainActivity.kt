package com.wordlockteacher

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wordlockteacher.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchEnableService.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) startUnlockService()
            else stopService(Intent(this, UnlockService::class.java))
        }

        binding.btnPreview.setOnClickListener {
            startActivity(Intent(this, LockScreenActivity::class.java))
        }

        binding.btnAddWord.setOnClickListener {
            startActivity(Intent(this, AddWordActivity::class.java))
        }

        updateCustomCount()
    }

    override fun onResume() {
        super.onResume()
        updateCustomCount()
    }

    private fun updateCustomCount() {
        lifecycleScope.launch {
            val count = WordRepository.getAllCustomWords().size
            binding.tvCustomCount.text = "Custom words added: $count"
        }
    }

    private fun startUnlockService() {
        val intent = Intent(this, UnlockService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}
