package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class StatisticsTest {

    @Test
    void 통계를_생성할_수_있다() {
        LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonusNumber = LottoNumber.from(7);
        WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);
        LottoGames lottoGames = new LottoGames(List.of(
            new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
        ));
        WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

        Statistics statistics = new Statistics(winningPrizes, 1.5);

        assertThat(statistics.winningPrizes()).isEqualTo(winningPrizes);
        assertThat(statistics.payoutRatio()).isEqualTo(1.5);
    }

    @Test
    void 같은_값으로_생성된_Statistics는_같다() {
        LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonusNumber = LottoNumber.from(7);
        WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);
        LottoGames lottoGames = new LottoGames(List.of(
            new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
        ));
        WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

        Statistics statistics1 = new Statistics(winningPrizes, 1.5);
        Statistics statistics2 = new Statistics(winningPrizes, 1.5);

        assertThat(statistics1).isEqualTo(statistics2);
    }

    @Test
    void 다른_수익률로_생성된_Statistics는_다르다() {
        LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonusNumber = LottoNumber.from(7);
        WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);
        LottoGames lottoGames = new LottoGames(List.of(
            new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
        ));
        WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

        Statistics statistics1 = new Statistics(winningPrizes, 1.5);
        Statistics statistics2 = new Statistics(winningPrizes, 2.0);

        assertThat(statistics1).isNotEqualTo(statistics2);
    }

    @Test
    void 수익률이_0인_통계를_생성할_수_있다() {
        LottoNumbers winningLottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonusNumber = LottoNumber.from(7);
        WinningNumbers winningNumbers = new WinningNumbers(winningLottoNumbers, bonusNumber);
        LottoGames lottoGames = new LottoGames(List.of(
            new LottoNumbers(List.of(10, 11, 12, 13, 14, 15))
        ));
        WinningPrizes winningPrizes = new WinningPrizes(winningNumbers, lottoGames);

        Statistics statistics = new Statistics(winningPrizes, 0.0);

        assertThat(statistics.payoutRatio()).isEqualTo(0.0);
    }
}
