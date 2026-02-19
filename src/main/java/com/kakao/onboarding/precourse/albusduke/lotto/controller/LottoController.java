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
        return Retry.onError(() -> {
            PurchaseAmount purchaseAmount = inputView.inputPurchaseAmount();

            ManualGameCount manualGameCount = inputView.inputManualGameCount();

            return lottoService.purchaseLottoGames(purchaseAmount, manualGameCount);
        }, outputView::outputError);
    }

    public LottoGames generate(PurchaseGameAmount purchaseGameAmount) {
        return Retry.onError(() -> {
            LottoGames manualGames = inputView.inputManualGames(purchaseGameAmount);

            outputView.outputPurchaseGameAmount(purchaseGameAmount);

            LottoGames autoGames = lottoService.purchaseAutoGames(purchaseGameAmount);

            outputView.outputLottoNumbers(autoGames);

            return manualGames.add(autoGames);
        }, outputView::outputError);
    }

    public WinningNumbers createWinningNumbers() {
        return Retry.onError(inputView::inputWinningNumbers, outputView::outputError);
    }

    public void calculateStatistics(WinningNumbers winningNumbers, LottoGames lottoGames) {
        Statistics statistics = statisticsService.calculateStatistics(winningNumbers,
            lottoGames);
        outputView.outputStatistics(statistics);
    }
}
