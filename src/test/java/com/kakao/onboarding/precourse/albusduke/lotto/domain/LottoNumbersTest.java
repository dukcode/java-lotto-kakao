package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LottoNumbersTest {
	@Test
	void 숫자_6개_미만으로_생성할_수_없다() {
		assertThatThrownBy(() ->
			new LottoNumbers(List.of(1, 2, 3, 4, 5))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 숫자_6개_초과로_생성할_수_없다() {
		assertThatThrownBy(() ->
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6, 7))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 범위_외_숫자로_생성할_수_없다() {
		assertThatThrownBy(() ->
			new LottoNumbers(List.of(50, 2, 3, 4, 5, 6))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 범위_내_숫자_6개로_생성할_수_있다() {
		assertThatCode(() ->
			new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
		).doesNotThrowAnyException();
	}

	@Test
	void 중복된_숫자로_생성할_수_없다() {
		assertThatThrownBy(() ->
			new LottoNumbers(List.of(1, 1, 3, 4, 5, 6))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 겹치는_숫자_갯수를_계산할_수_있다() {
		LottoNumbers a = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumbers b = new LottoNumbers(List.of(1, 2, 3, 40, 41, 42));

		Assertions.assertThat(a.countMatchingNumbers(a)).isEqualTo(6);
		Assertions.assertThat(a.countMatchingNumbers(b)).isEqualTo(3);
	}

	@Test
	void 특정_번호를_포함하는지_확인할_수_있다() {
		LottoNumbers lottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));

		assertThat(lottoNumbers.hasNumber(LottoNumber.from(1))).isTrue();
		assertThat(lottoNumbers.hasNumber(LottoNumber.from(6))).isTrue();
		assertThat(lottoNumbers.hasNumber(LottoNumber.from(7))).isFalse();
		assertThat(lottoNumbers.hasNumber(LottoNumber.from(45))).isFalse();
	}

	@Test
	void 로또_번호_리스트를_가져올_수_있다() {
		LottoNumbers lottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));

		assertThat(lottoNumbers.getLottoNumbers()).hasSize(6);
		assertThat(lottoNumbers.getLottoNumbers().get(0).getNumber()).isEqualTo(1);
		assertThat(lottoNumbers.getLottoNumbers().get(5).getNumber()).isEqualTo(6);
	}

	@Test
	void 겹치는_숫자가_없을_때_0을_반환한다() {
		LottoNumbers a = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumbers b = new LottoNumbers(List.of(40, 41, 42, 43, 44, 45));

		assertThat(a.countMatchingNumbers(b)).isEqualTo(0);
	}

	@Test
	void 모든_숫자가_겹칠_때_6을_반환한다() {
		LottoNumbers a = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
		LottoNumbers b = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));

		assertThat(a.countMatchingNumbers(b)).isEqualTo(6);
	}
}
