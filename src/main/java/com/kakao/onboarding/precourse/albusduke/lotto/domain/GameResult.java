package com.kakao.onboarding.precourse.albusduke.lotto.domain;

public record GameResult(int matchingCount, int bonusMatchingCount) {

    public boolean matches(int matchingCount, int bonusMatchingCount) {
        return matchingCount == this.matchingCount && bonusMatchingCount == this.bonusMatchingCount;
    }
}
