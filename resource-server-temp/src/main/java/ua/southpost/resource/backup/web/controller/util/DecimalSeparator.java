package ua.southpost.resource.backup.web.controller.util;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public enum DecimalSeparator {
	COMMA(',', Pattern.compile("([+-])?[0-9]+(,[0-9]{1,2})?")),
	DOT('.', Pattern.compile("[+-]?[0-9]+(\\.[0-9]{1,2})?"));

	private final char separatorChar;
	@Nonnull
	private final Pattern moneyPattern;

	DecimalSeparator(char separatorChar, @Nonnull Pattern moneyPattern) {
		this.separatorChar = separatorChar;
		this.moneyPattern = moneyPattern;
	}

	public char getSeparatorChar() {
		return separatorChar;
	}

	@Nonnull
	public Pattern getMoneyPattern() {
		return moneyPattern;
	}

	public String getMoneyPatternExpression() {
		return getMoneyPattern().pattern();
	}

	@Nonnull
	public static Optional<DecimalSeparator> find(char separatorChar) {
		return Arrays.stream(DecimalSeparator.values())
				.filter(s -> s.separatorChar == separatorChar)
				.findFirst();
	}
}
