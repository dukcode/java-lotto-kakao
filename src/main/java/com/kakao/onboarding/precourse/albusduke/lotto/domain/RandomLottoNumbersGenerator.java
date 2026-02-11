package com.kakao.onboarding.precourse.albusduke.lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class RandomLottoNumbersGenerator implements LottoNumbersGenerator {

    private final List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(1, 45).boxed().toList());

    @Override
    public LottoNumbers generate() {
        Collections.shuffle(numbers);
        return new LottoNumbers(numbers.subList(0, 6));
    }
}
