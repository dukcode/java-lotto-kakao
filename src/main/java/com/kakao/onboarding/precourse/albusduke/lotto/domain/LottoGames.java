package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import java.util.ArrayList;
import java.util.List;

public record LottoGames(List<LottoNumbers> games) {

	public LottoGames add(LottoGames other) {
		List<LottoNumbers> games = new ArrayList<>();
		games.addAll(this.games);
		games.addAll(other.games);
		return new LottoGames(games);
	}
}
