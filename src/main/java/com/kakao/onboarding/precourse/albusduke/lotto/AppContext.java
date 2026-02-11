package com.kakao.onboarding.precourse.albusduke.lotto;

import com.kakao.onboarding.precourse.albusduke.lotto.controller.LottoController;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.RandomLottoNumbersGenerator;
import com.kakao.onboarding.precourse.albusduke.lotto.service.LottoService;
import com.kakao.onboarding.precourse.albusduke.lotto.service.StatisticsService;
import com.kakao.onboarding.precourse.albusduke.lotto.util.Console;
import com.kakao.onboarding.precourse.albusduke.lotto.view.InputView;
import com.kakao.onboarding.precourse.albusduke.lotto.view.OutputView;

public class AppContext {
	private AppContext() {
	}

	public static LottoController lottoController() {
		Console console = new Console();

		OutputView outputView = new OutputView(console);
		InputView inputView = new InputView(console);

		LottoService lottoService = new LottoService(new RandomLottoNumbersGenerator());
		StatisticsService statisticsService = new StatisticsService();

		return new LottoController(inputView, outputView, lottoService, statisticsService);
	}

}
