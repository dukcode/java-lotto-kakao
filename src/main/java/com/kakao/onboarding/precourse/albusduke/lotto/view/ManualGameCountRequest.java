package com.kakao.onboarding.precourse.albusduke.lotto.view;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.ManualGameCount;

public record ManualGameCountRequest(int count) {

    public ManualGameCount toManualGameCount() {
        return new ManualGameCount(count);
    }
}
