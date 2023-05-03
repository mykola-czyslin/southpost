package ua.southpost.resource.backup.web.service.pagemodel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelMappingUtil {

	private ModelMappingUtil() {
	}

	public static void transferDataToModel(@Nonnull @ModelAttributesContainer final Object data, @Nonnull final Model model) {
		transferDataToConsumer(data, p -> model.addAttribute(p.getKey(), p.getValue()));
	}

	public static void transferDataToModelAndView(@Nonnull @ModelAttributesContainer final Object data, @Nonnull ModelAndView modelAndView) {
		transferDataToConsumer(data, p -> modelAndView.addObject(p.getKey(), p.getValue()));
	}

	private static void transferDataToConsumer(@Nonnull @ModelAttributesContainer final Object data, @Nonnull Consumer<Pair<String, Object>> consumer) {
		Class<?> dataType = data.getClass();
		final Map<String, String> attributeAliasMap = Maps.newConcurrentMap();//loadAttributesMap(dataType);
		while (dataType.getAnnotation(ModelAttributesContainer.class) != null) {
			attributeAliasMap.putAll(loadAttributesMap(dataType));
			Arrays.stream(dataType.getDeclaredFields())
					.flatMap(f -> modelAttributeStream(data, f))
					.map(p -> alias(p, attributeAliasMap))
					.forEach(consumer);
			dataType = dataType.getSuperclass();
		}

	}

	private static Map<String, String> loadAttributesMap(Class<?> dataType) {
		return Optional.ofNullable(dataType.getAnnotation(ModelAttributeAliases.class))
					.map(a -> Arrays.stream(a.value())).orElseGet(Stream::empty)
					.collect(Collectors.toMap(ModelAttributeAlias::aliasFor, ModelAttributeAlias::value));
	}

	private static Pair<String, Object> alias(@Nonnull Pair<String, Object> input, @Nonnull Map<String, String> aliasMap) {
		final String keyAlias = aliasMap.getOrDefault(input.getKey(), input.getKey());
		return Pair.of(keyAlias, input.getValue());
	}

	private static Stream<Pair<String, Object>> modelAttributeStream(@Nonnull Object source, @Nonnull Field field) {
		return Optional.ofNullable(field.getAnnotation(ModelAttributeMapping.class))
				.map(a -> get(source, field, a))
				.map(Collections::singleton)
				.map(Collection::stream)
				.orElseGet(() -> attributesOfContainer(source, field));
	}

	private static Stream<Pair<String, Object>> attributesOfContainer(@Nonnull Object source, @Nonnull Field field) {
		return Optional.of(field).filter(ModelMappingUtil::isModelAttributeContainerAndNotAttributeMapping)
				.map(f -> getFieldValue(source, f))
				.map(ModelMappingUtil::streamOfModelAttributes)
				.orElseGet(Stream::empty);
	}

	private static Stream<Pair<String, Object>> streamOfModelAttributes(@Nonnull final Object data) {
		Class<?> dataType = data.getClass();
		final Map<String, String> attributeAliasMap = Maps.newHashMap();
		List<Class<?>> dataTypeList = Lists.newLinkedList();
		do {
			attributeAliasMap.putAll(loadAttributesMap(dataType));
			dataTypeList.add(dataType);
			dataType = dataType.getSuperclass();
		} while (dataType.getAnnotation(ModelAttributesContainer.class) != null);
		return dataTypeList.stream().flatMap(cs -> Arrays.stream(cs.getDeclaredFields()))
				.map(f -> modelAttribute(data, f))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(p -> alias(p, attributeAliasMap));
	}

	private static Optional<Pair<String, Object>> modelAttribute(@Nonnull Object source, @Nonnull Field field) {
		return Optional.ofNullable(field.getAnnotation(ModelAttributeMapping.class))
				.map(a -> get(source, field, a));

	}

	private static boolean isModelAttributeContainerAndNotAttributeMapping(@Nonnull Field field) {
		return field.getAnnotation(ModelAttributeMapping.class) == null
				&& isModelAttributeContainerField(field);
	}

	private static boolean isModelAttributeContainerField(@Nonnull Field field) {
		return field.getAnnotation(ModelAttributesContainer.class) != null || field.getType().getAnnotation(ModelAttributesContainer.class) != null;
	}

	private static Pair<String, Object> get(@Nonnull Object source, @Nonnull Field field, ModelAttributeMapping mappingAnnotation) {
		return Pair.of(mappingAnnotation.modelAttributeName(), getFieldValue(source, field));
	}

	private static Object getFieldValue(@Nonnull Object source, @Nonnull Field field) {
		final boolean wasAccessible = field.isAccessible();
		try {
			field.setAccessible(true);
			return field.get(source);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} finally {
			if(field.isAccessible() ^ wasAccessible) {
				field.setAccessible(wasAccessible);
			}
		}
	}
}
