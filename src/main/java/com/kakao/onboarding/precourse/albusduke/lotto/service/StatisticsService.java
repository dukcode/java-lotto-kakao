package com.kakao.onboarding.precourse.albusduke.lotto.service;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningPrizes;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Prize;

import java.util.Map;

public class StatisticsService {
    public Statistics calculateStatistics(WinningNumbers winningNumbers, LottoGames lottoGames) {
        WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

        int gameCount = calculateGameCount(winningPrizes);
        long totalReward = calculateTotalReward(winningPrizes);

        return new Statistics(winningPrizes, calculateProfitRatio(gameCount, totalReward));
    }

    private static long calculateTotalReward(WinningPrizes winningPrizes) {
        Map<Prize, Integer> counts = winningPrizes.getCounts();

        long totalReward = 0L;
        for (Prize prize : Prize.values()) {
            totalReward += counts.getOrDefault(prize, 0) * prize.getReward();
        }
        return totalReward;
    }

    private static int calculateGameCount(WinningPrizes winningPrizes) {
        Map<Prize, Integer> counts = winningPrizes.getCounts();

        int gameCount = 0;
        for (Prize prize : Prize.values()) {
            gameCount += counts.getOrDefault(prize, 0);
        }
        return gameCount;
    }

    private static double calculateProfitRatio(int gameCount, long totalReward) {
        return gameCount == 0 ? 0 : totalReward / ((double) gameCount * 1000);
    }
}
