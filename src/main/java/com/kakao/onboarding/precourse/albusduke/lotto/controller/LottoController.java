package com.kakao.onboarding.precourse.albusduke.lotto.controller;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.service.LottoService;
import com.kakao.onboarding.precourse.albusduke.lotto.service.StatisticsService;
import com.kakao.onboarding.precourse.albusduke.lotto.view.InputView;
import com.kakao.onboarding.precourse.albusduke.lotto.view.LottoGamesRequest;
import com.kakao.onboarding.precourse.albusduke.lotto.view.ManualGameCountRequest;
import com.kakao.onboarding.precourse.albusduke.lotto.view.OutputView;
import com.kakao.onboarding.precourse.albusduke.lotto.view.PurchaseAmountRequest;
import com.kakao.onboarding.precourse.albusduke.lotto.view.WinningNumbersRequest;

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

    public PurchaseAmount prepareAmount() {
        return Retry.onError(() -> {
            PurchaseAmountRequest purchaseAmountRequest = inputView.inputPurchaseAmount();
            return lottoService.prepareAmount(purchaseAmountRequest);
        }, outputView::outputError);
    }

    public PurchaseGameAmount purchase(PurchaseAmount purchaseAmount) {
        return Retry.onError(() -> {
            ManualGameCountRequest manualGameCountRequest = inputView.inputManualGameCount();
            return lottoService.purchaseLottoGames(manualGameCountRequest, purchaseAmount);
        }, outputView::outputError);
    }

    public LottoGames generate(PurchaseGameAmount purchaseGameAmount) {
        return Retry.onError(() -> {
            LottoGamesRequest manualGamesRequest = inputView.inputManualGames(purchaseGameAmount);
            LottoGames manualGames = lottoService.purchaseManualGames(manualGamesRequest);
            outputView.outputPurchaseGameAmount(purchaseGameAmount);

            LottoGames autoGames = lottoService.purchaseAutoGames(purchaseGameAmount);
            outputView.outputLottoNumbers(autoGames);

            return lottoService.sumGames(manualGames, autoGames);
        }, outputView::outputError);
    }

    public WinningNumbers createWinningNumbers() {
        return Retry.onError(() -> {
            WinningNumbersRequest winningNumbersRequest = inputView.inputWinningNumbers();
            return lottoService.createWinningNumbers(winningNumbersRequest);
        }, outputView::outputError);
    }

    public void calculateStatistics(
        PurchaseAmount purchaseAmount,
        PurchaseGameAmount purchaseGameAmount,
        WinningNumbers winningNumbers, LottoGames lottoGames) {
        Statistics statistics = statisticsService.calculateStatistics(purchaseAmount,
            purchaseGameAmount, winningNumbers,
            lottoGames);
        outputView.outputStatistics(statistics);
    }
}
