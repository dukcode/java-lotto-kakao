package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumber implements Comparable<LottoNumber> {

    static final int MIN_NUMBER = 1;
    static final int MAX_NUMBER = 45;

    private static final String NUMBER_OUT_OF_RANGE_ERR_MSG = "1 미만 또는 45 초과 숫자는 입력할 수 없습니다.";

    private static final Map<Integer, LottoNumber> CACHE =
        IntStream.rangeClosed(MIN_NUMBER, MAX_NUMBER)
            .boxed()
            .collect(Collectors.toUnmodifiableMap(n -> n, LottoNumber::new));

    private final int number;

    private LottoNumber(int number) {
        this.number = number;
    }

    public static LottoNumber from(int number) {
        validateInRange(number);
        return CACHE.get(number);
    }

    private static void validateInRange(int number) {
        if (number > MAX_NUMBER || number < MIN_NUMBER) {
            throw new IllegalArgumentException(NUMBER_OUT_OF_RANGE_ERR_MSG);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LottoNumber that = (LottoNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(LottoNumber o) {
        return Integer.compare(this.number, o.number);
    }
}
