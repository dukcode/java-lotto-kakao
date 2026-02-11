package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WinningPrizesTest {

	private WinningPrizes winningPrizes;

	@BeforeEach
	void setUp() {
		LottoNumbers lottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber lottoNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(lottoNumbers, lottoNumber);
		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 7)),
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 8))
		));
		winningPrizes = new WinningPrizes(winningNumbers, lottoGames);
	}

	@Test
	void 주어진_WinningNumbers_와_LottoGames_에_대해_당첨_수를_계산할_수_있다() {
		assertThat(winningPrizes.getCounts().get(Prize.FIRST)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().get(Prize.SECOND)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().getOrDefault(Prize.NONE, 0)).isEqualTo(0);
	}

	@Test
	void 다양한_당첨_등급을_계산할_수_있다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),      // 1등
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 7)),      // 2등
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 8)),      // 3등
			new LottoNumbers(List.of(1, 2, 3, 4, 10, 11)),   // 4등
			new LottoNumbers(List.of(1, 2, 3, 10, 11, 12)),  // 5등
			new LottoNumbers(List.of(10, 11, 12, 13, 14, 15)) // 꽝
		));

		WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

		assertThat(winningPrizes.getCounts().get(Prize.FIRST)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().get(Prize.SECOND)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().get(Prize.THIRD)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().get(Prize.FORTH)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().get(Prize.FIFTH)).isEqualTo(1);
		assertThat(winningPrizes.getCounts().get(Prize.NONE)).isEqualTo(1);
	}

	@Test
	void 당첨되지_않은_게임이_있을_때_NONE_등급을_계산할_수_있다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(10, 11, 12, 13, 14, 15)),
			new LottoNumbers(List.of(20, 21, 22, 23, 24, 25))
		));

		WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

		assertThat(winningPrizes.getCounts().getOrDefault(Prize.FIRST, 0)).isEqualTo(0);
		assertThat(winningPrizes.getCounts().get(Prize.NONE)).isEqualTo(2);
	}

	@Test
	void 당첨_등급별_개수를_가져올_수_있다() {
		assertThat(winningPrizes.getCounts()).isNotNull();
		assertThat(winningPrizes.getCounts()).containsKey(Prize.FIRST);
		assertThat(winningPrizes.getCounts()).containsKey(Prize.SECOND);
	}
}
