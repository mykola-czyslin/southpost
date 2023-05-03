/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides utility method that allows to perform generic option generation.
 * Created by mchys on 21.04.2018.
 */
@Service
public class EnumOptionsUtil {

	@Resource(name = "messageSource")
	private MessageSource messageSource;
	private static final Object[] EMPTY_ARGS = new Object[0];

	public <T extends Enum<T>> Map<String, String> options(Class<T> enumType, final Locale locale, int width, Function<T, String> messageKeyExtractor, Comparator<T> comparator) {
		return Arrays.stream(enumType.getEnumConstants()).sorted(Optional.ofNullable(comparator).orElseGet(() -> Comparator.comparing(T::ordinal)))
				.collect(Collectors.toMap(Enum::name, e1 -> messageSource.getMessage(messageKeyExtractor.apply(e1), EMPTY_ARGS, locale), (a, b) -> b, () -> initOptionsMap(width)));
	}

	public <T extends Enum<T>> Map<String, String> options(Class<T> enumType, final Locale locale, Function<T, String> messageKeyExtractor, Comparator<T> comparator) {
		return Arrays.stream(enumType.getEnumConstants()).sorted(Optional.ofNullable(comparator).orElseGet(() -> Comparator.comparing(T::ordinal)))
				.collect(Collectors.toMap(Enum::name, e1 -> messageSource.getMessage(messageKeyExtractor.apply(e1), EMPTY_ARGS, locale), (a, b) -> b, LinkedHashMap::new));
	}

	private Map<String, String> initOptionsMap(int width) {
		Map<String, String> map = new LinkedHashMap<>();
		final int actualWidth = width < 1 ? OptionsUtilConstants.DEFAULT_WIDTH : (width / 2) * 2 + 1;
		map.put(OptionsUtilConstants.NULL_OPTION_VALUE, StringUtils.leftPad(OptionsUtilConstants.OPTION_TEXT_TERMINAL_CHAR, actualWidth, OptionsUtilConstants.OPTION_TEXT_PAD_STR));
		return map;
	}

}
