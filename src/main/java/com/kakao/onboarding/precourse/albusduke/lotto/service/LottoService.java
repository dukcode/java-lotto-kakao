package com.kakao.onboarding.precourse.albusduke.lotto.service;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbersGenerator;

import java.util.ArrayList;
import java.util.List;

public class LottoService {

    private static final int LOTTO_COST = 1_000;
    private static final String PURCHASE_MOUNT_NOT_DIVIDED_BY_LOTTO_COST = "로또 구매 단위는 " +  LOTTO_COST + "원 단위여야 합니다.";

    private final LottoNumbersGenerator lottoNumbersGenerator;

    public LottoService(LottoNumbersGenerator lottoNumbersGenerator) {
        this.lottoNumbersGenerator =  lottoNumbersGenerator;
    }

    public PurchaseGameAmount purchaseLottoGames(PurchaseAmount purchaseAmount) {
        validateDivisibleByLottoCost(purchaseAmount);

        return calculatePurchaseGameAmount(purchaseAmount);
    }

    private static void validateDivisibleByLottoCost(PurchaseAmount purchaseAmount) {
        if (purchaseAmount.purchaseAmount() % LOTTO_COST != 0) {
            throw new IllegalArgumentException(PURCHASE_MOUNT_NOT_DIVIDED_BY_LOTTO_COST);
        }
    }

    private PurchaseGameAmount calculatePurchaseGameAmount(PurchaseAmount purchaseAmount) {
        return new PurchaseGameAmount(purchaseAmount.purchaseAmount() / LOTTO_COST);
    }

    public LottoGames purchaseLottoGame(PurchaseGameAmount purchaseGameAmount) {
        List<LottoNumbers> lottoNumbers = new ArrayList<>();
        for (int i = 0; i < purchaseGameAmount.count(); ++i) {
            lottoNumbers.add(lottoNumbersGenerator.generate());
        }
        return new LottoGames(lottoNumbers);
    }
}
