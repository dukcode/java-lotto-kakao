package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class LottoNumbers {

	static final int NUMBER_SIZE = 6;
	private static final String NUMBER_SIZE_ERR_MSG = "숫자 6개로 생성할 수 있습니다.";
	private static final String DUPLICATED_NUMBER_ERR_MSG = "중복된 숫자로 생성할 수 없습니다.";

	private final List<LottoNumber> lottoNumbers;

	public LottoNumbers(List<Integer> numbers) {
		validateSize(numbers);
		validateUnique(numbers);

		lottoNumbers = numbers.stream().map(LottoNumber::from).toList();
	}

	private static void validateUnique(List<Integer> numbers) {
		Set<Integer> uniqueNumbers = new HashSet<>(numbers);
		if (uniqueNumbers.size() != numbers.size()) {
			throw new IllegalArgumentException(DUPLICATED_NUMBER_ERR_MSG);
		}
	}

	private static void validateSize(List<Integer> numbers) {
		if (numbers.size() != NUMBER_SIZE) {
			throw new IllegalArgumentException(NUMBER_SIZE_ERR_MSG);
		}
	}

	public boolean hasNumber(LottoNumber number) {
		return lottoNumbers.contains(number);
	}

	public int countMatchingNumbers(LottoNumbers otherNumbers) {
		return (int)otherNumbers.stream()
			.filter(this::hasNumber)
			.count();
	}

	public Stream<LottoNumber> stream() {
		return lottoNumbers.stream();
	}

	public List<LottoNumber> getLottoNumbers() {
		return lottoNumbers;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		LottoNumbers that = (LottoNumbers)o;
		return Objects.equals(lottoNumbers, that.lottoNumbers);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(lottoNumbers);
	}
}
