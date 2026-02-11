package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.GameResult;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Prize;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class PrizeTest {

    @Test
    void 당첨_조건에_따라_Prize를_생성할_수_있다() {
        assertThat(Prize.of(new GameResult(6, 0))).isEqualTo(Prize.FIRST);

        assertThat(Prize.of(new GameResult(5, 1))).isEqualTo(Prize.SECOND);

        assertThat(Prize.of(new GameResult(5, 0))).isEqualTo(Prize.THIRD);

        assertThat(Prize.of(new GameResult(4, 1))).isEqualTo(Prize.FORTH);
        assertThat(Prize.of(new GameResult(4, 0))).isEqualTo(Prize.FORTH);

        assertThat(Prize.of(new GameResult(3, 1))).isEqualTo(Prize.FIFTH);
        assertThat(Prize.of(new GameResult(3, 0))).isEqualTo(Prize.FIFTH);
    }

    @Test
    void 당첨_조건에_포함되지_않으면_NONE을_생성한다() {
        assertThat(Prize.of(new GameResult(2, 1))).isEqualTo(Prize.NONE);
        assertThat(Prize.of(new GameResult(2, 0))).isEqualTo(Prize.NONE);
        assertThat(Prize.of(new GameResult(1, 1))).isEqualTo(Prize.NONE);
        assertThat(Prize.of(new GameResult(1, 0))).isEqualTo(Prize.NONE);
        assertThat(Prize.of(new GameResult(0, 0))).isEqualTo(Prize.NONE);
    }

}
