package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PurchaseAmountTest {

    @Test
    void 구매_금액을_생성할_수_있다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(1000);

        assertThat(purchaseAmount.purchaseAmount()).isEqualTo(1000);
    }

    @Test
    void 최소_구매_금액을_생성할_수_있다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(0);

        assertThat(purchaseAmount.purchaseAmount()).isEqualTo(0);
    }

    @Test
    void 큰_구매_금액을_생성할_수_있다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(100000);

        assertThat(purchaseAmount.purchaseAmount()).isEqualTo(100000);
    }

    @Test
    void 같은_금액으로_생성된_PurchaseAmount는_같다() {
        PurchaseAmount purchaseAmount1 = new PurchaseAmount(1000);
        PurchaseAmount purchaseAmount2 = new PurchaseAmount(1000);

        assertThat(purchaseAmount1).isEqualTo(purchaseAmount2);
    }

    @Test
    void 다른_금액으로_생성된_PurchaseAmount는_다르다() {
        PurchaseAmount purchaseAmount1 = new PurchaseAmount(1000);
        PurchaseAmount purchaseAmount2 = new PurchaseAmount(2000);

        assertThat(purchaseAmount1).isNotEqualTo(purchaseAmount2);
    }
}
