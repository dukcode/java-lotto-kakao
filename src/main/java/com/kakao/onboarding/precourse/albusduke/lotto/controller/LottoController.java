package com.kakao.onboarding.precourse.albusduke.lotto.controller;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.service.LottoService;
import com.kakao.onboarding.precourse.albusduke.lotto.service.StatisticsService;
import com.kakao.onboarding.precourse.albusduke.lotto.view.InputView;
import com.kakao.onboarding.precourse.albusduke.lotto.view.ManualGameCount;
import com.kakao.onboarding.precourse.albusduke.lotto.view.OutputView;

public class LottoController {

	private final InputView inputView;
	private final OutputView outputView;

	private final LottoService lottoService;
	private final StatisticsService statisticsService;

	public LottoController(InputView inputView, OutputView outputView,
		LottoService lottoService, StatisticsService statisticsService) {
		this.inputView = inputView;
		this.outputView = outputView;
		this.lottoService = lottoService;
		this.statisticsService = statisticsService;
	}

	public PurchaseGameAmount purchase() {
		try {
			PurchaseAmount purchaseAmount = inputView.inputPurchaseAmount();
			ManualGameCount manualGameCount = inputView.inputManualGameCount();
			PurchaseGameAmount purchaseGameAmount = lottoService.purchaseLottoGames(purchaseAmount, manualGameCount);
			outputView.outputPurchaseGameAmount(purchaseGameAmount);
			return purchaseGameAmount;
		} catch (IllegalArgumentException e) {
			outputView.outputError(e);
			return purchase();
		}
	}

	public LottoGames generateNumbers(PurchaseGameAmount purchaseAmount) {
		try {
			LottoGames lottoGames = lottoService.purchaseLottoGame(purchaseAmount);
			outputView.outputLottoNumbers(lottoGames);
			return lottoGames;
		} catch (IllegalArgumentException e) {
			outputView.outputError(e);
			return generateNumbers(purchaseAmount);
		}
	}

	public WinningNumbers createWinningNumbers() {
		try {
			return inputView.inputWinningNumbers();
		} catch (IllegalArgumentException e) {
			outputView.outputError(e);
			return createWinningNumbers();
		}
	}

	public void calculateStatistics(WinningNumbers winningNumbers, LottoGames lottoGames) {
		try {
			Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);
			outputView.outputStatistics(statistics);
		} catch (IllegalArgumentException e) {
			outputView.outputError(e);
			createWinningNumbers();
		}
	}
}
