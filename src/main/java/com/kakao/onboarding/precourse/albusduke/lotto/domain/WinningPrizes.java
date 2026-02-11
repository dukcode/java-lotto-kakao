package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import java.util.HashMap;
import java.util.Map;

public class WinningPrizes {

    private final Map<Prize, Integer> counts = new HashMap<>();

    public WinningPrizes(WinningNumbers winningNumbers, LottoGames lottoGames) {
        for (LottoNumbers lottoNumbers : lottoGames.games()) {
            GameResult gameResult = winningNumbers.calculateResult(lottoNumbers);
            Prize prize = Prize.of(gameResult);
            counts.put(prize, counts.getOrDefault(prize, 0) + 1);
        }
    }

    public Map<Prize, Integer> getCounts() {
        return counts;
    }
}
