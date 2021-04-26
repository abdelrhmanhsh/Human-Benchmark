package com.abdelrahmman.humanbenchmark.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.abdelrahmman.humanbenchmark.R
import com.abdelrahmman.humanbenchmark.util.Constants

class VerbalMemoryFragment : Fragment() {

    // TODO: Save Score
    var lives = 3
    var score = 0
    var seen_words = mutableListOf<String>()

    private lateinit var btnStart : AppCompatButton
    private lateinit var btnSaveScore : AppCompatButton
    private lateinit var btnSeen : AppCompatButton
    private lateinit var btnNew : AppCompatButton
    private lateinit var linearStartGame : LinearLayout
    private lateinit var linearGameplay : LinearLayout
    private lateinit var linearEndResult : LinearLayout
    private lateinit var textLives : TextView
    private lateinit var textScore : TextView
    private lateinit var textWord : TextView
    private lateinit var textResult : TextView
    private lateinit var btnTryAgain : AppCompatButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verbal_memory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        linearStartGame = view.findViewById(R.id.verbal_start_game)
        linearGameplay = view.findViewById(R.id.gameplay_verbal)
        linearEndResult = view.findViewById(R.id.gameplay_result_verbal)

        textLives = view.findViewById(R.id.text_lives)
        textScore = view.findViewById(R.id.text_score)
        textWord = view.findViewById(R.id.text_word)
        textResult = view.findViewById(R.id.text_result)

        btnStart = view.findViewById(R.id.btn_start)
        btnSeen = view.findViewById(R.id.btn_seen)
        btnNew = view.findViewById(R.id.btn_new)
        btnSaveScore = view.findViewById(R.id.btn_save_score)
        btnTryAgain = view.findViewById(R.id.btn_try_again)

        btnStart.setOnClickListener {
            handleGameplay()
        }

        btnSaveScore.setOnClickListener {
            // TODO
        }

        btnTryAgain.setOnClickListener {
            handleTryAgain()
        }

    }

    private fun handleGameplay(){

        linearStartGame.visibility = View.GONE
        linearGameplay.visibility = View.VISIBLE

        textLives.setText("Lives | $lives")
        textScore.setText("Score | $score")

        var word = ""

        if (score <= 10){
            word = generateRandomWord10()

        } else if (score > 10 && score <= 30){
            word = generateRandomWord30()

        } else if (score > 30 && score <= 75){
            word = generateRandomWord75()

        } else {
            word = generateRandomWord()
        }

        textWord.setText(word)

        btnSeen.setOnClickListener {
            handleButtonSeen(word)
        }

        btnNew.setOnClickListener {
            handleButtonNew(word)
        }

        if (lives == 0){
            linearGameplay.visibility = View.GONE
            linearEndResult.visibility = View.VISIBLE

            textResult.setText("$score Words")
        }
    }

    private fun handleButtonSeen(word: String){
        if (seen_words.contains(word)){
            score++
        } else {
            lives--
        }

        seen_words.add(word)
        handleGameplay()
    }

    private fun handleButtonNew(word: String){
        if (!seen_words.contains(word)){
            score++
        } else {
            lives--
        }

        seen_words.add(word)
        handleGameplay()
    }

    private fun handleTryAgain(){
        lives = 3
        score = 0
        seen_words.clear()

        linearEndResult.visibility = View.GONE
        linearGameplay.visibility = View.VISIBLE

        handleGameplay()
    }

    private fun generateRandomWord10(): String {
        val randomWord = Constants.VOCAB10.random()
        return randomWord
    }

    private fun generateRandomWord30(): String {
        val randomWord = Constants.VOCAB30.random()
        return randomWord
    }

    private fun generateRandomWord75(): String {
        val randomWord = Constants.VOCAB75.random()
        return randomWord
    }

    private fun generateRandomWord(): String {
        val randomWord = Constants.VOCABULARY.random()
        return randomWord
    }

}