package ua.southpost.resource.commons.service;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.util.StackEntry;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class EntitySortOptionsServiceImpl implements EntitySortOptionsService {
	static final String MSG_KEY_OPTION_SELECT_ONE = "option.select.one";
	static final Object[] EMPTY_ARGS = new Object[0];

	private final Logger logger = LoggerFactory.getLogger(EntitySortOptionsServiceImpl.class);
	private final EntityInfoTypeToIdentityTypeResolver entityInfoTypeToIdentityTypeResolver;

	private final MessageSource messageSource;

	private final ConcurrentMap<Class<? extends EntityInfo<?>>, EntitySortOptions> entitySortOptionsCache;

	public EntitySortOptionsServiceImpl(EntityInfoTypeToIdentityTypeResolver entityInfoTypeToIdentityTypeResolver, MessageSource messageSource) {
		this.entityInfoTypeToIdentityTypeResolver = entityInfoTypeToIdentityTypeResolver;
		this.messageSource = messageSource;
		this.entitySortOptionsCache = new ConcurrentHashMap<>();
	}

	@Nonnull
	@Override
	public EntitySortOptions getSortOptions(@Nonnull Class<? extends EntityInfo<?>> entityType, @Nonnull Locale locale) {

		return entitySortOptionsCache.computeIfAbsent(entityType, t -> querySortOptions(t, locale));
	}

	@Nonnull
	private EntitySortOptions querySortOptions(@Nonnull Class<? extends EntityInfo<?>> entityType, @Nonnull Locale locale) {
		@Nonnull final Class<? extends Identity<?>> identityType = entityInfoTypeToIdentityTypeResolver.resolveIdentityType(entityType);
		@Nonnull final Map<String, String> options = sortFields(identityType, null, null, locale);
		return new EntitySortOptions(options);
	}

	@Nonnull
	private Map<String, String> sortFields(@Nonnull Class<? extends Identity<?>> identityType, @Nullable String keyPrefix, @Nullable StackEntry<String> valueMessageKeyStack, @Nonnull Locale locale) {
		return Arrays.stream(identityType.getDeclaredFields())
				.map(f -> Pair.of(f, AnnotationUtils.getAnnotation(f, SortField.class)))
				.filter(p -> p.getValue() != null)
				.sorted(Comparator.comparingInt(p -> (p.getValue().complex() ? 1 : 0)))
				.flatMap(p -> getOptionsStream(keyPrefix, valueMessageKeyStack, locale, p))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p1, () -> Maps.newLinkedHashMap(Collections.singletonMap("", this.getMessage(MSG_KEY_OPTION_SELECT_ONE, locale, EMPTY_ARGS)))));
	}

	@SuppressWarnings("unchecked")
	private Stream<Map.Entry<String, String>> getOptionsStream(@Nullable String keyPrefix, @Nullable StackEntry<String> valuePrefixMessageKeyStack, Locale locale, Pair<Field, SortField> pair) {
		return pair.getValue().complex() && Identity.class.isAssignableFrom(pair.getKey().getType()) ?
				sortFields((Class<? extends Identity<?>>) pair.getKey().getType(), getCanonicalName(keyPrefix, pair), new StackEntry<>(pair.getValue().label(), valuePrefixMessageKeyStack), locale).entrySet().stream() :
				Stream.of(new ImmutablePair<>(getCanonicalName(keyPrefix, pair), getValueText(valuePrefixMessageKeyStack, locale, this.getMessage(pair.getValue().label(), locale, EMPTY_ARGS))));
	}

	private String getValueText(StackEntry<String> valuePrefixMessageKeyStack, Locale locale, String localValueText) {
		return valuePrefixMessageKeyStack == null ?
				localValueText :
				getValueText(valuePrefixMessageKeyStack.pop(), locale, this.getMessage(valuePrefixMessageKeyStack.getValue(), locale, new String[]{localValueText}));
	}

	private String getMessage(@Nonnull String key, @Nonnull Locale locale, Object[] arguments) {
		final String message = messageSource.getMessage(key, arguments, locale);
		if(isBlank(message)) {
			logger.warn("Fail to get message for the {} key", key);
			// TODO: wrap key in some markup?
			return key;
		}
		return message;
	}

	private static String getCanonicalName(String prefix, Pair<Field, SortField> pair) {
		return (isBlank(prefix) ? "" : prefix + ".") + pair.getKey().getName();
	}

}
