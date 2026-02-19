package com.kakao.onboarding.precourse.albusduke.lotto.service;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbersGenerator;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.ManualGameCount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.view.LottoGamesRequest;
import com.kakao.onboarding.precourse.albusduke.lotto.view.ManualGameCountRequest;
import com.kakao.onboarding.precourse.albusduke.lotto.view.PurchaseAmountRequest;
import com.kakao.onboarding.precourse.albusduke.lotto.view.WinningNumbersRequest;
import java.util.ArrayList;
import java.util.List;

public class LottoService {

    private static final int LOTTO_COST = 1_000;

    private final LottoNumbersGenerator lottoNumbersGenerator;

    public LottoService(LottoNumbersGenerator lottoNumbersGenerator) {
        this.lottoNumbersGenerator = lottoNumbersGenerator;
    }

    public PurchaseGameAmount purchaseLottoGames(PurchaseAmountRequest purchaseAmountRequest,
        ManualGameCountRequest manualGameCountRequest) {
        PurchaseAmount purchaseAmount = purchaseAmountRequest.toPurchaseAmount();
        ManualGameCount manualGameCount = manualGameCountRequest.toManualGameCount();
        return PurchaseGameAmount.of(purchaseAmount, LOTTO_COST, manualGameCount);
    }

    public LottoGames purchaseAutoGames(PurchaseGameAmount purchaseGameAmount) {
        List<LottoNumbers> lottoNumbers = new ArrayList<>();
        for (int i = 0; i < purchaseGameAmount.autoCount(); ++i) {
            lottoNumbers.add(lottoNumbersGenerator.generate());
        }
        return new LottoGames(lottoNumbers);
    }

    public LottoGames purchaseManualGames(LottoGamesRequest manualGamesRequest) {
        return manualGamesRequest.toLottoGames();
    }

    public LottoGames sumGames(LottoGames manualGames, LottoGames autoGames) {
        return manualGames.add(autoGames);
    }

    public WinningNumbers createWinningNumbers(WinningNumbersRequest winningNumbersRequest) {
        return winningNumbersRequest.toWinningNumbers();
    }
}
