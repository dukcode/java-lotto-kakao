package com.kakao.onboarding.precourse.albusduke.lotto.view;

import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoGames;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumber;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.LottoNumbers;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Prize;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.PurchaseGameAmount;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.Statistics;
import com.kakao.onboarding.precourse.albusduke.lotto.domain.WinningPrizes;
import com.kakao.onboarding.precourse.albusduke.lotto.util.Output;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private static final String PURCHASE_COUNT_FORMAT = "수동으로 %d장, 자동으로 %d개를 구매했습니다.";
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
        output.output(
            String.format(PURCHASE_COUNT_FORMAT, purchasedGameAmount.manualCount(),
                purchasedGameAmount.autoCount()));
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
            .append(createPrizeOutput(Prize.FIFTH, winningPrizes))
            .append("\n")
            .append(createPrizeOutput(Prize.FORTH, winningPrizes))
            .append("\n")
            .append(createPrizeOutput(Prize.THIRD, winningPrizes))
            .append("\n")
            .append(createPrizeOutput(Prize.SECOND, winningPrizes))
            .append("\n")
            .append(createPrizeOutput(Prize.FIRST, winningPrizes))
            .append("\n")
            .append(String.format(RATIO_FORMAT, statistics.payoutRatio()));

        output.output(sb.toString());
    }

    private String createPrizeOutput(Prize prize, WinningPrizes winningPrizes) {
        return String.format(MATCHING_FORMAT, prize.getMatchingCount()) +
            (prize.getBonusMatchingCount() != 0 ? (", " + BONUS_MATCHING_FORMAT) : "") +
            String.format(REWARD_FORMAT, prize.getReward()) +
            " - " +
            String.format(COUNT_FORMAT, winningPrizes.getCountOf(prize));
    }

    public void outputError(IllegalArgumentException e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
}
