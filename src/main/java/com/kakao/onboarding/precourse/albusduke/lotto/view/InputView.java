package com.kakao.onboarding.precourse.albusduke.lotto.view;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumber;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.util.Input;
import com.kakao.onboarding.precourse.albusduke.lotto.util.Output;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputView {

    private static final String PURCHASE_AMOUNT_REQUEST = "구입금액을 입력해 주세요.";
    private static final String MANUAL_GAME_COUNT_REQUEST = "수동으로 구매할 로또 수를 입력해 주세요.";
    private static final String WINNING_NUMBERS_REQUEST = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String BONUS_NUMBER_REQUEST = "보너스 볼을 입력해 주세요.";

    private static final String ILLEGAL_NUMBERS = "입력 형식은 숫자여야 합니다. (ex: 1, 2, 3, 4, 5, 6)";
    private static final String ILLEGAL_NUMBER = "입력 형식은 숫자 형식이어야 합니다.";

    private final Input input;
    private final Output output;

    public InputView(Input input, Output output) {
        this.input = input;
        this.output = output;
    }


    public PurchaseAmount inputPurchaseAmount() {
        output.output(PURCHASE_AMOUNT_REQUEST);

        int purchaseAmount = parseInt();

        return new PurchaseAmount(purchaseAmount);
    }

    public ManualGameCount inputManualGameCount() {
        output.output(MANUAL_GAME_COUNT_REQUEST);

        int manualGameCount = parseInt();

        return new ManualGameCount(manualGameCount);
    }

    public LottoGames inputManualGames(ManualGameCount manualGameCount) {
        output.output("수동으로 구매할 번호를 입력해 주세요.");

        List<LottoNumbers> manualGames = new ArrayList<>();
        for (int i = 0; i < manualGameCount.count(); ++i) {
            manualGames.add(new LottoNumbers(parseIntegers()));
        }

        return new LottoGames(manualGames);
    }

    public WinningNumbers inputWinningNumbers() {
        output.output(WINNING_NUMBERS_REQUEST);

        List<Integer> winningNumbers = parseIntegers();

        output.output(BONUS_NUMBER_REQUEST);
        int bonusNumber = parseInt();

        return new WinningNumbers(new LottoNumbers(winningNumbers), LottoNumber.from(bonusNumber));
    }

    private List<Integer> parseIntegers() {
        try {
            return Arrays.stream(input.readNext().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ILLEGAL_NUMBERS);
        }
    }

    private int parseInt() {
        try {
            return Integer.parseInt(input.readNext());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ILLEGAL_NUMBER);
        }
    }

}
