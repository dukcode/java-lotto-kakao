package com.kakao.onboarding.precourse.albusduke.lotto.util;

import java.util.Scanner;

public class Console implements Input, Output {

	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String readNext() {
		return scanner.nextLine().trim();
	}

	@Override
	public void output(String output) {
		System.out.println(output);
	}
}
