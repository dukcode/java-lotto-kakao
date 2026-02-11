package com.kakao.onboarding.precourse.albusduke.lotto;

import com.kakao.onboarding.precourse.albusduke.lotto.controller.LottoController;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;

public class App {
	public static void main(String[] args) {
		LottoController lottoController = AppContext.lottoController();
		runLottoGame(lottoController);
	}

	private static void runLottoGame(LottoController lottoController) {
		PurchaseGameAmount purchaseGameAmount = lottoController.calculatePurchaseGameAmount();
		LottoGames lottoGames = lottoController.purchaseLottoGame(purchaseGameAmount);
		WinningNumbers winningNumbers = lottoController.createWinningNumbers();
		lottoController.calculateStatistics(winningNumbers, lottoGames);
	}
}
