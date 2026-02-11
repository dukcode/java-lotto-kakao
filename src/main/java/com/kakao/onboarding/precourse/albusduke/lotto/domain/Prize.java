package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import java.util.Arrays;

/**
 * 로또 당첨 등급을 관리하는 Enum입니다.
 * <p>
 * <b>주의:</b> {@link #of(GameResult)} 메서드에서 순차적 비교를 수행하므로,
 * 반드시 당첨 금액이나 조건이 까다로운 순서(내림차순)로 선언해야 합니다.
 */
public enum Prize {

	FIRST(6, 0, 2_000_000_000L),
	SECOND(5, 1, 30_000_000L),
	THIRD(5, 0, 1_500_000L),
	FORTH(4, 0, 50_000L),
	FIFTH(3, 0, 5_000L),
	NONE(0, 0, 0L);

	private final int matchingCount;
	private final int bonusMatchingCount;
	private final long reward;

	Prize(int matchingCount, int bonusMatchingCount, long reward) {
		this.matchingCount = matchingCount;
		this.bonusMatchingCount = bonusMatchingCount;
		this.reward = reward;
	}

	/**
	 * 게임 결과에 해당하는 당첨 등급을 반환합니다.
	 * * @param gameResult 로또 비교 결과
	 * @return 일치하는 {@link Prize}, 없으면 {@link #NONE}
	 * @implNote 선언된 순서대로 검사를 진행하므로, 보너스 번호 일치가 필요한 등급이
	 * 일반 일치 등급보다 우선순위가 높아야 합니다.
	 */
	public static Prize of(GameResult gameResult) {
		return Arrays.stream(Prize.values())
			.filter(prize -> prize.matches(gameResult))
			.findFirst()
			.orElse(Prize.NONE);
	}

	private boolean matches(GameResult result) {
		return this.matchingCount == result.matchingCount()
			&& this.bonusMatchingCount <= result.bonusMatchingCount();
	}

	public int getMatchingCount() {
		return matchingCount;
	}

	public int getBonusMatchingCount() {
		return bonusMatchingCount;
	}

	public long getReward() {
		return reward;
	}
}
