package com.kakao.onboarding.precourse.albusduke.lotto.domain;

public class WinningNumbers {

	private static final String BONUS_NUMBER_DUPLICATED_ERR_MSG = "보너스 번호는 당첨 번호에 포함될 수 없습니다.";

	private final LottoNumbers winningNumbers;
	private final LottoNumber bonusNumber;

	public WinningNumbers(LottoNumbers winningNumbers, LottoNumber bonusNumber) {
		validateBonusInNumbers(winningNumbers, bonusNumber);
		this.winningNumbers = winningNumbers;
		this.bonusNumber = bonusNumber;
	}

	private static void validateBonusInNumbers(LottoNumbers lottoNumbers, LottoNumber bonusNumber) {
		if (lottoNumbers.hasNumber(bonusNumber)) {
			throw new IllegalArgumentException(BONUS_NUMBER_DUPLICATED_ERR_MSG);
		}
	}

	public GameResult calculateResult(LottoNumbers game) {
		int matchingCount = game.countMatchingNumbers(winningNumbers);
		int bonusMatchingCount = calculateBonusMatchCount(game);

		return new GameResult(matchingCount, bonusMatchingCount);
	}

	private int calculateBonusMatchCount(LottoNumbers game) {
		if (game.hasNumber(bonusNumber)) {
			return 1;
		}

		return 0;
	}
}
