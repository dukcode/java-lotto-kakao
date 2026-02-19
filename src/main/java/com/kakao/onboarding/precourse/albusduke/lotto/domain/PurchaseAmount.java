package com.kakao.onboarding.precourse.albusduke.lotto.domain;

public record PurchaseAmount(int purchaseAmount) {

    private static final int MIN_PURCHASE_AMOUNT = 0;
    private static final int MAX_PURCHASE_AMOUNT = 100_000;
    private static final String ILLEGAL_PURCHASE_AMOUNT =
        "구매금액은 " + MIN_PURCHASE_AMOUNT + " 초과, " + MAX_PURCHASE_AMOUNT + " 미만이어야 합니다";

    public PurchaseAmount {
        if (purchaseAmount <= MIN_PURCHASE_AMOUNT || MAX_PURCHASE_AMOUNT < purchaseAmount) {
            throw new IllegalArgumentException(ILLEGAL_PURCHASE_AMOUNT);
        }
    }
}
