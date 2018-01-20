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

        fun updateFirstPlayerCards()

        fun updateSecondPlayerCards()

        fun clearAllView()
    }

    /**
     * View call presenter
     */
    interface presenter : BaseUserActionListener {

        fun analysisResult()

        fun startNewGame()

    }
}