package com.kakao.onboarding.precourse.albusduke.lotto.service;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Prize;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningPrizes;
import java.util.Map;

public class StatisticsService {

    private static long calculateTotalReward(WinningPrizes winningPrizes) {
        Map<Prize, Integer> counts = winningPrizes.getCounts();

        long totalReward = 0L;
        for (Prize prize : Prize.values()) {
            totalReward += counts.getOrDefault(prize, 0) * prize.getReward();
        }
        return totalReward;
    }

    private static double calculateProfitRatio(PurchaseAmount purchaseAmount,
        WinningPrizes winningPrizes) {
        int gameCount = purchaseAmount.gameCount();

        if (gameCount == 0) {
            return 0;
        }

        return (double) calculateTotalReward(winningPrizes) / gameCount;
    }

    public Statistics calculateStatistics(
        PurchaseAmount purchaseAmount, PurchaseGameAmount purchaseGameAmount,
        WinningNumbers winningNumbers, LottoGames lottoGames) {
        WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

        return new Statistics(winningPrizes, calculateProfitRatio(purchaseAmount, winningPrizes));
    }
}
