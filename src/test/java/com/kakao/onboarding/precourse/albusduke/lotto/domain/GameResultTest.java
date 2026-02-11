package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.GameResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameResultTest {

    @Test
    void 게임_결과를_생성할_수_있다() {
        GameResult gameResult = new GameResult(3, 0);

        assertThat(gameResult.matchingCount()).isEqualTo(3);
        assertThat(gameResult.bonusMatchingCount()).isEqualTo(0);
    }

    @Test
    void 보너스_번호가_일치한_게임_결과를_생성할_수_있다() {
        GameResult gameResult = new GameResult(5, 1);

        assertThat(gameResult.matchingCount()).isEqualTo(5);
        assertThat(gameResult.bonusMatchingCount()).isEqualTo(1);
    }

    @Test
    void 같은_값으로_생성된_GameResult는_같다() {
        GameResult gameResult1 = new GameResult(3, 0);
        GameResult gameResult2 = new GameResult(3, 0);

        assertThat(gameResult1).isEqualTo(gameResult2);
    }

    @Test
    void 다른_값으로_생성된_GameResult는_다르다() {
        GameResult gameResult1 = new GameResult(3, 0);
        GameResult gameResult2 = new GameResult(4, 0);

        assertThat(gameResult1).isNotEqualTo(gameResult2);
    }

    @Test
    void 보너스_번호_일치_여부에_따라_다르다() {
        GameResult gameResult1 = new GameResult(5, 0);
        GameResult gameResult2 = new GameResult(5, 1);

        assertThat(gameResult1).isNotEqualTo(gameResult2);
    }

    @Test
    void 일치_개수가_0인_게임_결과를_생성할_수_있다() {
        GameResult gameResult = new GameResult(0, 0);

        assertThat(gameResult.matchingCount()).isEqualTo(0);
        assertThat(gameResult.bonusMatchingCount()).isEqualTo(0);
    }

    @Test
    void 최대_일치_개수인_게임_결과를_생성할_수_있다() {
        GameResult gameResult = new GameResult(6, 0);

        assertThat(gameResult.matchingCount()).isEqualTo(6);
        assertThat(gameResult.bonusMatchingCount()).isEqualTo(0);
    }
}
