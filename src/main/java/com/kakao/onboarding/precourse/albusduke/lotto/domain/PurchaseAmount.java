package com.kakao.onboarding.precourse.albusduke.lotto.domain;

public record PurchaseAmount(int purchaseAmount) {

    private static final int COST_PER_GAME = 1_000;
    private static final int MIN_PURCHASE_AMOUNT = 0;
    private static final int MAX_PURCHASE_AMOUNT = 100_000;
    private static final String ILLEGAL_PURCHASE_AMOUNT =
        "구매금액은 " + MIN_PURCHASE_AMOUNT + " 초과, " + MAX_PURCHASE_AMOUNT + " 이하여야 합니다";
    private static final String PURCHASE_AMOUNT_NOT_DIVISIBLE =
        "로또 구매 단위는 " + COST_PER_GAME + "원 단위여야 합니다.";

    public PurchaseAmount {
        validateRange(purchaseAmount);
        validateDivisibleByCost(purchaseAmount);
    }

    private static void validateRange(int purchaseAmount) {
        if (purchaseAmount <= MIN_PURCHASE_AMOUNT || MAX_PURCHASE_AMOUNT < purchaseAmount) {
            throw new IllegalArgumentException(ILLEGAL_PURCHASE_AMOUNT);
        }
    }

    private static void validateDivisibleByCost(int purchaseAmount) {
        if (purchaseAmount % COST_PER_GAME != 0) {
            throw new IllegalArgumentException(PURCHASE_AMOUNT_NOT_DIVISIBLE);
        }
    }

    public int gameCount() {
        return purchaseAmount / COST_PER_GAME;
    }
}
