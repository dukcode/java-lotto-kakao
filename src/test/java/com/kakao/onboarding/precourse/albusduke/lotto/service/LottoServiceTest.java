package com.kakao.onboarding.precourse.albusduke.lotto.service;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbersGenerator;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class LottoServiceTest {

    private LottoService lottoService;
    private FixedLottoNumbersGenerator fixedGenerator;

    @BeforeEach
    void setUp() {
        fixedGenerator = new FixedLottoNumbersGenerator();
        lottoService = new LottoService(fixedGenerator);
    }

    @Test
    void 구매_금액이_1000원_단위로_나누어떨어지면_PurchaseGameAmount를_반환한다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        
        PurchaseGameAmount result = lottoService.purchaseLottoGames(purchaseAmount);
        
        assertThat(result.count()).isEqualTo(5);
    }

    @Test
    void 구매_금액이_1000원_단위로_나누어떨어지지_않으면_예외를_발생시킨다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(1500);
        
        assertThatThrownBy(() -> lottoService.purchaseLottoGames(purchaseAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("로또 구매 단위는 1000원 단위여야 합니다.");
    }

    @Test
    void 구매_금액이_0원이면_0개의_게임을_반환한다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(0);
        
        PurchaseGameAmount result = lottoService.purchaseLottoGames(purchaseAmount);
        
        assertThat(result.count()).isEqualTo(0);
    }

    @Test
    void 구매_금액이_1000원이면_1개의_게임을_반환한다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(1000);
        
        PurchaseGameAmount result = lottoService.purchaseLottoGames(purchaseAmount);
        
        assertThat(result.count()).isEqualTo(1);
    }

    @Test
    void purchaseLottoGame은_생성기를_지정된_횟수만큼_호출한다() {
        PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(3);
        fixedGenerator.setFixedLottoNumbers(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)));
        
        LottoGames result = lottoService.purchaseLottoGame(purchaseGameAmount);
        
        assertThat(fixedGenerator.getCallCount()).isEqualTo(3);
        assertThat(result.games()).hasSize(3);
    }

    @Test
    void purchaseLottoGame은_생성된_로또_번호들을_LottoGames로_반환한다() {
        PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(2);
        LottoNumbers lottoNumbers1 = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        LottoNumbers lottoNumbers2 = new LottoNumbers(List.of(7, 8, 9, 10, 11, 12));
        
        fixedGenerator.setLottoNumbersSequence(List.of(lottoNumbers1, lottoNumbers2));
        
        LottoGames result = lottoService.purchaseLottoGame(purchaseGameAmount);
        
        assertThat(result.games()).hasSize(2);
        assertThat(result.games().get(0)).isEqualTo(lottoNumbers1);
        assertThat(result.games().get(1)).isEqualTo(lottoNumbers2);
    }

    @Test
    void purchaseLottoGame은_0개의_게임을_요청하면_빈_LottoGames를_반환한다() {
        PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(0);
        
        LottoGames result = lottoService.purchaseLottoGame(purchaseGameAmount);
        
        assertThat(fixedGenerator.getCallCount()).isEqualTo(0);
        assertThat(result.games()).isEmpty();
    }

    @Test
    void purchaseLottoGame은_여러_번_호출해도_정상적으로_동작한다() {
        PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(2);
        fixedGenerator.setFixedLottoNumbers(new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)));
        
        LottoGames result1 = lottoService.purchaseLottoGame(purchaseGameAmount);
        LottoGames result2 = lottoService.purchaseLottoGame(purchaseGameAmount);
        
        assertThat(result1.games()).hasSize(2);
        assertThat(result2.games()).hasSize(2);
        assertThat(fixedGenerator.getCallCount()).isEqualTo(4);
    }

    /**
     * 테스트용 고정값을 반환하는 LottoNumbersGenerator 구현체
     */
    private static class FixedLottoNumbersGenerator implements LottoNumbersGenerator {
        private LottoNumbers fixedLottoNumbers;
        private List<LottoNumbers> lottoNumbersSequence;
        private int callCount = 0;
        private int sequenceIndex = 0;

        public void setFixedLottoNumbers(LottoNumbers lottoNumbers) {
            this.fixedLottoNumbers = lottoNumbers;
            this.lottoNumbersSequence = null;
            this.sequenceIndex = 0;
        }

        public void setLottoNumbersSequence(List<LottoNumbers> sequence) {
            this.lottoNumbersSequence = new ArrayList<>(sequence);
            this.fixedLottoNumbers = null;
            this.sequenceIndex = 0;
        }

        @Override
        public LottoNumbers generate() {
            callCount++;
            
            if (lottoNumbersSequence != null && !lottoNumbersSequence.isEmpty()) {
                LottoNumbers result = lottoNumbersSequence.get(sequenceIndex % lottoNumbersSequence.size());
                sequenceIndex++;
                return result;
            }
            
            if (fixedLottoNumbers != null) {
                return fixedLottoNumbers;
            }
            
            // 기본값 반환
            return new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        }

        public int getCallCount() {
            return callCount;
        }
    }
}
