package com.lemoandasan.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.lemoandasan.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sliderValue = 0
    private var targetValue = Random.nextInt(0, 100)
    private var totalScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.targetTextView.text = targetValue.toString()
        binding.gameScoreTextView?.text = getString(R.string.score_text, 0)
        binding.gameRoundTextView?.text = getString(R.string.round_text, 1)

        binding.hitMeButton.setOnClickListener {
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }

        })
    }

    private fun pointsForCurrentRound(): Int {
        return 100 - abs(targetValue - sliderValue)
    }

    private fun showResult() {
        val dialogTitle = getString(R.string.result_dialog_title)
        val dialogMessage = getString(R.string.result_dialog_message, sliderValue,
            pointsForCurrentRound())
        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}