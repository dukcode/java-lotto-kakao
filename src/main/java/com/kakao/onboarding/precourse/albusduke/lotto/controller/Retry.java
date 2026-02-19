package com.kakao.onboarding.precourse.albusduke.lotto.controller;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Retry {

    private Retry() {
    }

    public static <T> T onError(Supplier<T> action,
        Consumer<IllegalArgumentException> onError) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                onError.accept(e);
            }
        }
    }
}
