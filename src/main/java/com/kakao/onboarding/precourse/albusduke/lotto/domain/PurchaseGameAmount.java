package com.kakao.onboarding.precourse.albusduke.lotto.domain;

public record PurchaseGameAmount(int autoCount, int manualCount) {

    private static final String MANUAL_COUNT_EXCEEDS_TOTAL_COUNT = "수동 구매 금액이 총 구매 금액을 초과할 수 없습니다.";

    public static PurchaseGameAmount of(PurchaseAmount purchaseAmount,
        ManualGameCount manualGameCount) {
        int totalCount = purchaseAmount.gameCount();
        int manualCount = manualGameCount.count();
        int autoCount = totalCount - manualCount;

        validateManualCountDoesNotExceedTotal(totalCount, manualCount);

        return new PurchaseGameAmount(autoCount, manualCount);
    }

    private static void validateManualCountDoesNotExceedTotal(int totalCount, int manualCount) {
        if (totalCount < manualCount) {
            throw new IllegalArgumentException(MANUAL_COUNT_EXCEEDS_TOTAL_COUNT);
        }
    }
}
