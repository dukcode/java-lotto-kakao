package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import com.kakao.onboarding.precourse.albusduke.lotto.view.ManualGameCount;

public record PurchaseGameAmount(int autoCount, int manualCount) {

	private static final String PURCHASE_MOUNT_NOT_DIVIDED_BY_LOTTO_COST_FORMAT = "로또 구매 단위는 %d원 단위여야 합니다.";

	public static PurchaseGameAmount of(PurchaseAmount purchaseAmount, int lottoCost, ManualGameCount manualGameCount) {
		validateDivisibleByLottoCost(purchaseAmount, lottoCost);

		int totalCount = purchaseAmount.purchaseAmount() / lottoCost;
		int autoCount = totalCount - manualGameCount.count();
		int manualCount = manualGameCount.count();

		return new PurchaseGameAmount(autoCount, manualCount);
	}

	private static void validateDivisibleByLottoCost(PurchaseAmount purchaseAmount, int lottoCost) {
		if (purchaseAmount.purchaseAmount() % lottoCost != 0) {
			throw new IllegalArgumentException(
				String.format(PURCHASE_MOUNT_NOT_DIVIDED_BY_LOTTO_COST_FORMAT, lottoCost));
		}
	}
}
