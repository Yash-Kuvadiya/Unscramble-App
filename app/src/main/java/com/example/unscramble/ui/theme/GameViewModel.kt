package com.example.unscramble.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel(){

    var userGuess by mutableStateOf("")

    private val _uiState = MutableStateFlow(GameUIState(currentScrambledWord = pickRandomWordAndShuffle()))
    val uiState:StateFlow<GameUIState> = _uiState.asStateFlow()

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord : String


    // pick the word
    private fun pickRandomWordAndShuffle():String{
        currentWord= allWords.random()

        if (usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        }
        else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }


    // shuffle word

    private fun shuffleCurrentWord(word : String):String{
        val tempWord = word.toCharArray()

        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    init {
        resetGame()
    }
    // reset word
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUIState(currentScrambledWord    = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

}