package com.kakao.onboarding.precourse.albusduke.lotto;

import com.kakao.onboarding.precourse.albusduke.lotto.controller.LottoController;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;

public class App {

    public static void main(String[] args) {
        LottoController lottoController = AppContext.lottoController();
        runLottoGame(lottoController);
    }

    private static void runLottoGame(LottoController lottoController) {
        PurchaseAmount purchaseAmount = lottoController.prepareAmount();
        PurchaseGameAmount purchaseGameAmount = lottoController.purchase(purchaseAmount);
        LottoGames lottoGames = lottoController.generate(purchaseGameAmount);
        WinningNumbers winningNumbers = lottoController.createWinningNumbers();
        lottoController.calculateStatistics(purchaseAmount, purchaseGameAmount, winningNumbers,
            lottoGames);
    }
}
