package com.kakao.onboarding.precourse.albusduke.lotto.view;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import java.util.List;

public record LottoGamesRequest(List<List<Integer>> games) {

    public LottoGames toLottoGames() {
        return new LottoGames(games.stream().map(LottoNumbers::new).toList());
    }
}
