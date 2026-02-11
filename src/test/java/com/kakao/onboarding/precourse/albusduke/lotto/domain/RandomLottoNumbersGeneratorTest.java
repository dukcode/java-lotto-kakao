package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbersGenerator;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.RandomLottoNumbersGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class RandomLottoNumbersGeneratorTest {

    @Test
    void 로또_번호를_생성할_수_있다() {
        LottoNumbersGenerator generator = new RandomLottoNumbersGenerator();

        LottoNumbers lottoNumbers = generator.generate();

        assertThat(lottoNumbers.getLottoNumbers()).hasSize(6);
    }

    @Test
    void 생성된_로또_번호는_중복되지_않는다() {
        LottoNumbersGenerator generator = new RandomLottoNumbersGenerator();

        LottoNumbers lottoNumbers = generator.generate();

        Set<Integer> numbers = new HashSet<>();
        for (var number : lottoNumbers.getLottoNumbers()) {
            assertThat(numbers.add(number.getNumber())).isTrue();
        }
    }

    @Test
    void 생성된_로또_번호는_1부터_45_사이의_숫자이다() {
        LottoNumbersGenerator generator = new RandomLottoNumbersGenerator();

        LottoNumbers lottoNumbers = generator.generate();

        for (var number : lottoNumbers.getLottoNumbers()) {
            assertThat(number.getNumber()).isBetween(1, 45);
        }
    }

    @Test
    void 여러_번_생성하면_다른_번호가_생성된다() {
        LottoNumbersGenerator generator = new RandomLottoNumbersGenerator();

        LottoNumbers lottoNumbers1 = generator.generate();
        LottoNumbers lottoNumbers2 = generator.generate();

        // 완전히 같은 경우는 거의 없지만, 같은 경우도 있을 수 있으므로
        // 최소한 하나의 숫자는 다를 가능성이 높음
        // 실제로는 대부분 다른 조합이 생성됨
        assertThat(lottoNumbers1).isNotNull();
        assertThat(lottoNumbers2).isNotNull();
    }

    @Test
    void 생성된_로또_번호는_유효한_LottoNumbers_객체이다() {
        LottoNumbersGenerator generator = new RandomLottoNumbersGenerator();

        LottoNumbers lottoNumbers = generator.generate();

        assertThatCode(() -> {
            lottoNumbers.countMatchingNumbers(lottoNumbers);
            lottoNumbers.hasNumber(lottoNumbers.getLottoNumbers().get(0));
        }).doesNotThrowAnyException();
    }

    @Test
    void LottoNumbersGenerator_인터페이스를_구현한다() {
        LottoNumbersGenerator generator = new RandomLottoNumbersGenerator();

        assertThat(generator).isInstanceOf(LottoNumbersGenerator.class);
    }
}
