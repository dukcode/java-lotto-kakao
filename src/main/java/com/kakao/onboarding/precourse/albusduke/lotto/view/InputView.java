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
	private static final String ILLEGAL_PURCHASE_AMOUNT_ERR_MSG =
		"구매금액은 " + MIN_PURCHASE_AMOUNT + " 초과, " + MAX_PURCHASE_AMOUNT + " 미만이어야 합니다";
	private static final String WINNING_NUMBERS_REQUEST = "지난 주 당첨 번호를 입력해 주세요.";
	private static final String BONUS_NUMBER_REQUEST = "보너스 볼을 입력해 주세요.";
	private static final String ILLEGAL_WINNING_NUMBERS_ERR_MSG = "입력 형식은 숫자여야 합니다. (ex: 1, 2, 3, 4, 5, 6)";

	private final Input input;

	public InputView(Input input) {
		this.input = input;
	}

	public PurchaseAmount inputPurchaseAmount() {
		System.out.println(PURCHASE_AMOUNT_REQUEST);
		try {
			int purchaseAmount = Integer.parseInt(input.readNext());

			if (purchaseAmount <= MIN_PURCHASE_AMOUNT || MAX_PURCHASE_AMOUNT < purchaseAmount) {
				throw new IllegalArgumentException(ILLEGAL_PURCHASE_AMOUNT_ERR_MSG);
			}
			return new PurchaseAmount(purchaseAmount);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("숫자 형식이어야 합니다.");
		}
	}

	public WinningNumbers inputWinningNumbers() {
		System.out.println(WINNING_NUMBERS_REQUEST);

		List<Integer> winningNumbers;
		try {
			winningNumbers = Arrays.stream(input.readNext().split(", ")).map(Integer::parseInt).toList();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(ILLEGAL_WINNING_NUMBERS_ERR_MSG);
		}
		System.out.println(BONUS_NUMBER_REQUEST);
		int bonusNumber = Integer.parseInt(input.readNext());

		return new WinningNumbers(new LottoNumbers(winningNumbers), LottoNumber.from(bonusNumber));
	}
}
