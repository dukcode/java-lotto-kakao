package com.kakao.onboarding.precourse.albusduke.lotto.view;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;

public record PurchaseAmountRequest(int purchaseAmount) {

    public PurchaseAmount toPurchaseAmount() {
        return new PurchaseAmount(purchaseAmount);
    }
}
