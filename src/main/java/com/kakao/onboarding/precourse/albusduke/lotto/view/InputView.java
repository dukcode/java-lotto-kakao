package com.kakao.onboarding.precourse.albusduke.lotto.view;

import java.util.Arrays;
import java.util.List;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumber;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.util.Input;

public class InputView {

	private static final int MIN_PURCHASE_AMOUNT = 0;
	private static final int MAX_PURCHASE_AMOUNT = 100_000;

	private static final String PURCHASE_AMOUNT_REQUEST = "구입금액을 입력해 주세요.";
	private static final String WINNING_NUMBERS_REQUEST = "지난 주 당첨 번호를 입력해 주세요.";
	private static final String BONUS_NUMBER_REQUEST = "보너스 볼을 입력해 주세요.";

	private static final String ILLEGAL_NUMBERS = "입력 형식은 숫자여야 합니다. (ex: 1, 2, 3, 4, 5, 6)";
	private static final String ILLEGAL_NUMBER = "입력 형식은 숫자 형식이어야 합니다.";
	private static final String ILLEGAL_PURCHASE_AMOUNT =
		"구매금액은 " + MIN_PURCHASE_AMOUNT + " 초과, " + MAX_PURCHASE_AMOUNT + " 미만이어야 합니다";

	private final Input input;

	public InputView(Input input) {
		this.input = input;
	}

	public PurchaseAmount inputPurchaseAmount() {
		System.out.println(PURCHASE_AMOUNT_REQUEST);

		int purchaseAmount = parseInt();

		if (purchaseAmount <= MIN_PURCHASE_AMOUNT || MAX_PURCHASE_AMOUNT < purchaseAmount) {
			throw new IllegalArgumentException(ILLEGAL_PURCHASE_AMOUNT);
		}

		return new PurchaseAmount(purchaseAmount);
	}

	public WinningNumbers inputWinningNumbers() {
		System.out.println(WINNING_NUMBERS_REQUEST);

		List<Integer> winningNumbers = parseIntegers();

		System.out.println(BONUS_NUMBER_REQUEST);
		int bonusNumber = parseInt();

		return new WinningNumbers(new LottoNumbers(winningNumbers), LottoNumber.from(bonusNumber));
	}

	private List<Integer> parseIntegers() {
		try {
			return Arrays.stream(input.readNext().split(", ")).map(Integer::parseInt).toList();
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
