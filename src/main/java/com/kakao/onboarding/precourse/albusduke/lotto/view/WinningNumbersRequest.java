package com.kakao.onboarding.precourse.albusduke.lotto.view;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumber;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import java.util.List;

public record WinningNumbersRequest(List<Integer> winningNumbers, int bonusNumber) {

    public WinningNumbers toWinningNumbers() {
        return new WinningNumbers(new LottoNumbers(winningNumbers), LottoNumber.from(bonusNumber));
    }
}
