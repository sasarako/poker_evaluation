package com.sasara.pokergame.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sasara.pokergame.R
import kotlinx.android.synthetic.main.activity_main.*

class PokerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_title_textView.text = "test"
    }
}
