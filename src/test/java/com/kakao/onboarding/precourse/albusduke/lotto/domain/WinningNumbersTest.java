package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WinningNumbersTest {

	private WinningNumbers winningNumbers;

	@BeforeEach
	void setUp() {
		winningNumbers = new WinningNumbers(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)), LottoNumber.from(7));
	}

	@Test
	void LottoNumber_와_보너스번호로_생성할_수_있다() {
		assertThatCode(() ->
			new WinningNumbers(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)), LottoNumber.from(7))
		).doesNotThrowAnyException();
	}

	@Test
	void LottoNumbers_와_보너스_숫자가_겹칠_수_없다() {
		assertThatThrownBy(() ->
			new WinningNumbers(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)), LottoNumber.from(1))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void GameResult_를_계산할_수_있다() {
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))))
			.isEqualTo(new GameResult(6, 0));
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 4, 5, 7))))
			.isEqualTo(new GameResult(5, 1));
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 4, 5, 10))))
			.isEqualTo(new GameResult(5, 0));
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 4, 10, 11))))
			.isEqualTo(new GameResult(4, 0));
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 4, 7, 10))))
			.isEqualTo(new GameResult(4, 1));
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 7, 10, 11))))
			.isEqualTo(new GameResult(3, 1));
		assertThat(winningNumbers.calculateResult(new LottoNumbers(List.of(1, 2, 3, 10, 11, 12))))
			.isEqualTo(new GameResult(3, 0));
	}

}
