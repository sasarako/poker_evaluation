package com.sasara.pokergame.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.sasara.pokergame.R
import com.sasara.pokergame.dataprovider.RuleAnalysis
import com.sasara.pokergame.dataprovider.datasource.OnHandCardsProviderImpl
import com.sasara.pokergame.usecase.CardAddRemoveUseCaseImpl
import com.sasara.pokergame.usecase.CardAnalysisUseCase
import com.sasara.pokergame.usecase.CompareResultUseCase
import kotlinx.android.synthetic.main.activity_main.*

class PokerActivity : AppCompatActivity(), PokerContract.View {

    lateinit var presenter: PokerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val p1CardProvider = OnHandCardsProviderImpl()
        val p1CardAddingUseCase = CardAddRemoveUseCaseImpl(p1CardProvider)
        val p1AnalysisUseCase = CardAnalysisUseCase(RuleAnalysis(), p1CardProvider)

        val p2CardProvider = OnHandCardsProviderImpl()
        val p2CardAddingUseCase = CardAddRemoveUseCaseImpl(p2CardProvider)
        val p2AnalysisUseCase = CardAnalysisUseCase(RuleAnalysis(), p2CardProvider)

        val compareUseCase = CompareResultUseCase(p1AnalysisUseCase, p2AnalysisUseCase)

        presenter = PokerPresenter(view = this,
                p1OnHandCardAddRemoveUseCase = p1CardAddingUseCase,
                p2OnHandCardAddRemoveUseCase = p2CardAddingUseCase,
                compareResultUseCase = compareUseCase)

        poker_firstPlayerCard_editText.setOnEditorActionListener { v: TextView?,
                                                                   actionId: Int?,
                                                                   event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                v?.text?.let {
                    presenter.addFirstPlayerCards(it.toString())
                    poker_firstPlayerCard_editText.setText("")
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        poker_secondPlayerCard_editText.setOnEditorActionListener { v: TextView?,
                                                                    actionId: Int?,
                                                                    event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                v?.text?.let {
                    presenter.addSecondPlayerCards(it.toString())
                    poker_secondPlayerCard_editText.setText("")
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        poker_calculate_button.setOnClickListener({
            presenter.analysisResult()
        })

        poker_newGame_button.setOnClickListener {
            presenter.startNewGame()
        }
    }

    override fun showGameResult(resultMsg: String) {
        poker_result_textView.text = resultMsg
    }

    override fun updateFirstPlayerCards(denotedCards: String) {
        firstPlayerOnHandCard_textView.text = denotedCards
    }

    override fun updateSecondPlayerCards(denotedCards: String) {
        secondPlayerOnHandCard_textView.text = denotedCards
    }

    override fun showErrorMsg(errorMsg: String) {
        Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
    }

    override fun clearAllView() {
        firstPlayerOnHandCard_textView.text = ""
        secondPlayerOnHandCard_textView.text = ""
        poker_result_textView.text = ""
    }
}
