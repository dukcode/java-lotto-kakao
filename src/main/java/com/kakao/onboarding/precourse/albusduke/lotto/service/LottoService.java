package com.kakao.onboarding.precourse.albusduke.lotto.service;

import java.util.ArrayList;
import java.util.List;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbersGenerator;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.view.ManualGameCount;

public class LottoService {

	private static final int LOTTO_COST = 1_000;

	private final LottoNumbersGenerator lottoNumbersGenerator;

	public LottoService(LottoNumbersGenerator lottoNumbersGenerator) {
		this.lottoNumbersGenerator = lottoNumbersGenerator;
	}

	public PurchaseGameAmount purchaseLottoGames(PurchaseAmount purchaseAmount, ManualGameCount manualGameCount) {
		return PurchaseGameAmount.of(purchaseAmount, LOTTO_COST, manualGameCount);
	}

	public LottoGames purchaseLottoGame(PurchaseGameAmount purchaseGameAmount) {
		List<LottoNumbers> lottoNumbers = new ArrayList<>();
		for (int i = 0; i < purchaseGameAmount.autoCount(); ++i) {
			lottoNumbers.add(lottoNumbersGenerator.generate());
		}
		return new LottoGames(lottoNumbers);
	}
}
