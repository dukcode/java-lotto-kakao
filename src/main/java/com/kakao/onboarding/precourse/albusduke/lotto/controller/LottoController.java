package com.kakao.onboarding.precourse.albusduke.lotto.controller;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.service.LottoService;
import com.kakao.onboarding.precourse.albusduke.lotto.service.StatisticsService;
import com.kakao.onboarding.precourse.albusduke.lotto.view.InputView;
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

	public PurchaseGameAmount calculatePurchaseGameAmount() {
		try {
			PurchaseAmount purchaseAmount = inputView.inputPurchaseAmount();
			PurchaseGameAmount purchaseGameAmount = lottoService.purchaseLottoGames(purchaseAmount);
			outputView.outputPurchaseGameAmount(purchaseGameAmount);
			return purchaseGameAmount;
		} catch (IllegalArgumentException e) {
			outputView.outputError(e);
			return calculatePurchaseGameAmount();
		}
	}

	public LottoGames purchaseLottoGame(PurchaseGameAmount purchaseAmount) {
		try {
			LottoGames lottoGames = lottoService.purchaseLottoGame(purchaseAmount);
			outputView.outputLottoNumbers(lottoGames);
			return lottoGames;
		} catch (IllegalArgumentException e) {
			outputView.outputError(e);
			return purchaseLottoGame(purchaseAmount);
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
