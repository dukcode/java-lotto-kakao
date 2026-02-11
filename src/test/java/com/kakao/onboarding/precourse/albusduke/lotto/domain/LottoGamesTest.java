package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class LottoGamesTest {

    @Test
    void 로또_게임들을_생성할_수_있다() {
        List<LottoNumbers> games = List.of(
                new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),
                new LottoNumbers(List.of(7, 8, 9, 10, 11, 12))
        );
        LottoGames lottoGames = new LottoGames(games);

        assertThat(lottoGames.games()).hasSize(2);
        assertThat(lottoGames.games().get(0)).isEqualTo(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)));
        assertThat(lottoGames.games().get(1)).isEqualTo(new LottoNumbers(List.of(7, 8, 9, 10, 11, 12)));
    }

    @Test
    void 빈_게임_리스트로_생성할_수_있다() {
        LottoGames lottoGames = new LottoGames(List.of());

        assertThat(lottoGames.games()).isEmpty();
    }

    @Test
    void 여러_게임으로_생성할_수_있다() {
        List<LottoNumbers> games = List.of(
                new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)),
                new LottoNumbers(List.of(7, 8, 9, 10, 11, 12)),
                new LottoNumbers(List.of(13, 14, 15, 16, 17, 18)),
                new LottoNumbers(List.of(19, 20, 21, 22, 23, 24)),
                new LottoNumbers(List.of(25, 26, 27, 28, 29, 30))
        );
        LottoGames lottoGames = new LottoGames(games);

        assertThat(lottoGames.games()).hasSize(5);
    }

    @Test
    void 같은_게임_리스트로_생성된_LottoGames는_같다() {
        List<LottoNumbers> games = List.of(
                new LottoNumbers(List.of(1, 2, 3, 4, 5, 6))
        );
        LottoGames lottoGames1 = new LottoGames(games);
        LottoGames lottoGames2 = new LottoGames(games);

        assertThat(lottoGames1).isEqualTo(lottoGames2);
    }
}
