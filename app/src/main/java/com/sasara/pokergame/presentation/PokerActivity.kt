package com.sasara.pokergame.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sasara.pokergame.R
import com.sasara.pokergame.data.Card
import com.sasara.pokergame.dataprovider.RuleAnalysis
import kotlinx.android.synthetic.main.activity_main.*

class PokerActivity : AppCompatActivity() {

    lateinit var presenter: PokerPresenter

    val cards: List<Card> = listOf(
            Card(donoted = "2H"),
            Card(donoted = "4D"),
            Card(donoted = "3S"),
            Card(donoted = "6C"),
            Card(donoted = "5D"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cards.forEach {
            Log.d("koko", "${it.getRank()}")
        }

        val rule: RuleAnalysis = RuleAnalysis(cards)
        presenter = PokerPresenter()

    }
}
