package com.wordlockteacher

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordApp : Application() {
    override fun onCreate() {
        super.onCreate()
        WordRepository.initialize(this)
        CoroutineScope(Dispatchers.IO).launch {
            WordRepository.seedDatabaseIfEmpty()
        }
    }
}
