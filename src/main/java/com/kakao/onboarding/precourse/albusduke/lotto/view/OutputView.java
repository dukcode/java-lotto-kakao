package com.kakao.onboarding.precourse.albusduke.lotto.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumber;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Prize;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningPrizes;
import com.kakao.onboarding.precourse.albusduke.lotto.util.Output;

public class OutputView {

	private static final String PURCHASE_COUNT_FORMAT = "%d개를 구매했습니다.";
	private static final String STATISTICS_PREFIX = "당첨 통계";
	private static final String SEPARATE_LINE = "-------------";
	private static final String MATCHING_FORMAT = "%d개 일치";
	private static final String REWARD_FORMAT = "(%d원)";
	private static final String BONUS_MATCHING_FORMAT = "보너스 볼 일치";
	private static final String COUNT_FORMAT = "%d개";
	private static final String RATIO_FORMAT = "총 수익률은 %.2f입니다.(기준이 1이기 때문에 결과적으로 손해라는 의미임)";

	private final Output output;

	public OutputView(Output output) {
		this.output = output;
	}

	public void outputPurchaseGameAmount(PurchaseGameAmount purchasedGameAmount) {
		output.output(String.format(PURCHASE_COUNT_FORMAT, purchasedGameAmount.count()));
	}

	public void outputLottoNumbers(LottoGames lottoGames) {
		for (LottoNumbers game : lottoGames.games()) {
			StringJoiner sj = new StringJoiner(", ", "[", "]");
			List<LottoNumber> sortedLottoNumbers = new ArrayList<>(game.getLottoNumbers());
			Collections.sort(sortedLottoNumbers);
			for (LottoNumber number : sortedLottoNumbers) {
				sj.add(String.valueOf(number.getNumber()));
			}
			System.out.println(sj);
		}
	}

	public void outputStatistics(Statistics statistics) {
		StringBuilder sb = new StringBuilder();

		WinningPrizes winningPrizes = statistics.winningPrizes();

		sb.append(STATISTICS_PREFIX).append("\n")
			.append(SEPARATE_LINE).append("\n")
			.append(createOutput(Prize.FIFTH, winningPrizes.getCounts().getOrDefault(Prize.FIFTH, 0))).append("\n")
			.append(createOutput(Prize.FORTH, winningPrizes.getCounts().getOrDefault(Prize.FORTH, 0))).append("\n")
			.append(createOutput(Prize.THIRD, winningPrizes.getCounts().getOrDefault(Prize.THIRD, 0))).append("\n")
			.append(createOutput(Prize.SECOND, winningPrizes.getCounts().getOrDefault(Prize.SECOND, 0))).append("\n")
			.append(createOutput(Prize.FIRST, winningPrizes.getCounts().getOrDefault(Prize.FIRST, 0))).append("\n")
			.append(String.format(RATIO_FORMAT, statistics.ratio()));

		output.output(sb.toString());
	}

	private String createOutput(Prize prize, int count) {
		return String.format(MATCHING_FORMAT, prize.getMatchingCount()) +
			(prize.getBonusMatchingCount() != 0 ? (", " + BONUS_MATCHING_FORMAT) : "") +
			String.format(REWARD_FORMAT, prize.getReward()) +
			" - " +
			String.format(COUNT_FORMAT, count);
	}

	public void outputError(IllegalArgumentException e) {
		System.out.println("[ERROR] " + e.getMessage());
	}
}
