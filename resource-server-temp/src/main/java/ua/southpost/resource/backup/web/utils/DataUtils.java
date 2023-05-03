/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.replace;

/**
 * Provides a set of utility methods useful for data conversion.
 * Created by mchys on 08.04.2018.
 */
public class DataUtils {

	public static final Pattern SQL_ESCAPE_CHARS = Pattern.compile("([\\\\%_])");

	public static <T> T nullWhenMatch(T value, Predicate<T> predicate) {
		return predicate.test(value) ? null : value;
	}

	public static String prepareOptionalPatternOrNullWhenMatch(String value, Predicate<String> predicate) {
		return prepareOptionalPattern(nullWhenMatch(value, predicate));
	}

	public static String prepareOptionalPatternOrNullWhenEmpty(String value) {
		return prepareOptionalPattern(nullWhenMatch(value, StringUtils::isEmpty));
	}

	private static String prepareOptionalPattern(String rawPattern) {
		return Optional.ofNullable(rawPattern)
				.filter(StringUtils::isNotBlank)
				.map(DataUtils::preparePattern)
				.orElse(null);
	}

	private static String preparePattern(String withWildcards) {
		return replace(
				replace(escapeSqlWildCards(withWildcards), "*", "%"),
				"?",
				"_"
		);
	}

	private static String escapeSqlWildCards(String sqlPattern) {
		return SQL_ESCAPE_CHARS.matcher(sqlPattern).replaceAll("\\\\$1");
	}
}
