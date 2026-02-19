package com.kakao.onboarding.precourse.albusduke.lotto.domain;

public record GameResult(int matchingCount, int bonusMatchingCount) {

    public boolean matches(int matchingCount, int bonusMatchingCount) {
        if (bonusMatchingCount == 0) {
            return this.matchingCount == matchingCount;
        }
        return this.matchingCount == matchingCount && this.bonusMatchingCount == bonusMatchingCount;
    }
}
