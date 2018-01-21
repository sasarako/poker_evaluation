package com.sasara.pokergame.presentation

import com.sasara.pokergame.common.BaseUserActionListener

/**
 * Created by sasara on 17/1/2018 AD.
 */
interface PokerContract {

    /**
     * Presenter call view
     */
    interface View {

        fun showGameResult(resultMsg: String)

        fun updateFirstPlayerCards(denotedCards: String)

        fun updateSecondPlayerCards(denotedCards: String)

        fun showErrorMsg(errorMsg: String)

        fun showErrorAddingFail()

        fun showErrorP1NotEnoughCard()

        fun showErrorP2NotEnoughCard()

        fun clearP1View()

        fun clearP2View()

        fun clearAllView()
    }

    /**
     * View call presenter
     */
    interface presenter : BaseUserActionListener {

        fun addFirstPlayerCards(denotedCard: String)

        fun addSecondPlayerCards(denotedCard: String)

        fun analysisResult()

        fun clearP1Cards()

        fun clearP2Cards()

        fun startNewGame()

    }
}