package com.wordlockteacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wordlockteacher.databinding.ItemWordCardBinding

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private var words: List<WordEntry> = emptyList()

    fun submitList(newWords: List<WordEntry>) {
        words = newWords
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int = words.size

    class WordViewHolder(private val binding: ItemWordCardBinding) : 
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: WordEntry) {
            binding.apply {
                tvLanguage.text = SupportedLanguage.values()
                    .find { it.code == word.languageCode }?.displayName ?: word.languageCode
                tvWord.text = word.word
                tvMeaning.text = word.meaning
                tvPronunciation.text = word.pronunciation?.let { "[$it]" } ?: ""
                tvPronunciation.visibility = 
                    if (word.pronunciation != null) View.VISIBLE else View.GONE

                val colorRes = when(word.languageCode) {
                    "SANS" -> R.color.sanskrit_color
                    "NEP" -> R.color.nepali_color
                    "ENG" -> R.color.english_color
                    else -> R.color.default_color
                }
                cardContainer.setCardBackgroundColor(
                    root.context.getColor(colorRes)
                )
            }
        }
    }
}
