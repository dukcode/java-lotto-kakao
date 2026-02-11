package com.kakao.onboarding.precourse.albusduke.lotto.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumber;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Prize;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;

public class StatisticsServiceTest {

	private StatisticsService statisticsService;

	@BeforeEach
	void setUp() {
		statisticsService = new StatisticsService();
	}

	@Test
	void 통계를_계산할_수_있다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
		));

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		assertThat(statistics.winningPrizes()).isNotNull();
		assertThat(statistics.ratio()).isGreaterThanOrEqualTo(0);
	}

	@Test
	void 당첨되지_않은_경우_수익률은_0이다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(10, 11, 12, 13, 14, 15))
		));

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		assertThat(statistics.ratio()).isEqualTo(0.0);
	}

	@Test
	void 빈_게임_리스트의_경우_수익률은_0이다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of());

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		assertThat(statistics.ratio()).isEqualTo(0.0);
	}

	@Test
	void 다양한_당첨_등급의_통계를_계산할_수_있다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),      // 1등
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 7)),      // 2등
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 8)),      // 3등
			new LottoNumbers(List.of(1, 2, 3, 4, 10, 11)),    // 4등
			new LottoNumbers(List.of(1, 2, 3, 10, 11, 12))   // 5등
		));

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		assertThat(statistics.winningPrizes()).isNotNull();
		assertThat(statistics.winningPrizes().getCounts().get(Prize.FIRST)).isEqualTo(1);
		assertThat(statistics.winningPrizes().getCounts().get(Prize.SECOND)).isEqualTo(1);
		assertThat(statistics.winningPrizes().getCounts().get(Prize.THIRD)).isEqualTo(1);
		assertThat(statistics.winningPrizes().getCounts().get(Prize.FORTH)).isEqualTo(1);
		assertThat(statistics.winningPrizes().getCounts().get(Prize.FIFTH)).isEqualTo(1);
		assertThat(statistics.ratio()).isGreaterThan(0);
	}

	@Test
	void 수익률을_정확히_계산한다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		// 1등 1개 (2,000,000,000원) - 구매 금액 1,000원
		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
		));

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		// 수익률 = 2,000,000,000 / (1 * 1,000) = 2,000,000
		assertThat(statistics.ratio()).isEqualTo(2_000_000.0);
	}

	@Test
	void 여러_게임의_수익률을_계산한다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		// 5등 2개 (각 5,000원) - 구매 금액 2,000원
		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 10, 11, 12)),
			new LottoNumbers(List.of(1, 2, 3, 13, 14, 15))
		));

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		// 수익률 = (5,000 * 2) / (2 * 1,000) = 10,000 / 2,000 = 5.0
		assertThat(statistics.ratio()).isEqualTo(5.0);
	}

	@Test
	void 당첨_등급별_개수가_정확히_계산된다() {
		LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumber bonusNumber = LottoNumber.from(7);
		WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);

		LottoGames lottoGames = new LottoGames(List.of(
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),      // 1등
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),      // 1등
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 7)),      // 2등
			new LottoNumbers(List.of(1, 2, 3, 4, 10, 11)),    // 4등
			new LottoNumbers(List.of(1, 2, 3, 4, 10, 11))    // 4등
		));

		Statistics statistics = statisticsService.calculateStatistics(winningNumbers, lottoGames);

		assertThat(statistics.winningPrizes().getCounts().get(Prize.FIRST)).isEqualTo(2);
		assertThat(statistics.winningPrizes().getCounts().get(Prize.SECOND)).isEqualTo(1);
		assertThat(statistics.winningPrizes().getCounts().getOrDefault(Prize.THIRD, 0)).isEqualTo(0);
		assertThat(statistics.winningPrizes().getCounts().get(Prize.FORTH)).isEqualTo(2);
	}
}
