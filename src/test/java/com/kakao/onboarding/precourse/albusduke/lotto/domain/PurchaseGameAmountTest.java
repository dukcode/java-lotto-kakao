package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PurchaseGameAmountTest {

	@Test
	void 구매_게임_수를_생성할_수_있다() {
		PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(5, 0);

		assertThat(purchaseGameAmount.autoCount()).isEqualTo(5);
	}

	@Test
	void 최소_구매_게임_수를_생성할_수_있다() {
		PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(0, 0);

		assertThat(purchaseGameAmount.autoCount()).isEqualTo(0);
	}

	@Test
	void 큰_구매_게임_수를_생성할_수_있다() {
		PurchaseGameAmount purchaseGameAmount = new PurchaseGameAmount(100, 0);

		assertThat(purchaseGameAmount.autoCount()).isEqualTo(100);
	}

	@Test
	void 같은_게임_수로_생성된_PurchaseGameAmount는_같다() {
		PurchaseGameAmount purchaseGameAmount1 = new PurchaseGameAmount(5, 0);
		PurchaseGameAmount purchaseGameAmount2 = new PurchaseGameAmount(5, 0);

		assertThat(purchaseGameAmount1).isEqualTo(purchaseGameAmount2);
	}

	@Test
	void 다른_게임_수로_생성된_PurchaseGameAmount는_다르다() {
		PurchaseGameAmount purchaseGameAmount1 = new PurchaseGameAmount(5, 0);
		PurchaseGameAmount purchaseGameAmount2 = new PurchaseGameAmount(10, 0);

		assertThat(purchaseGameAmount1).isNotEqualTo(purchaseGameAmount2);
	}
}
