package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.SortOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;

class EntityGridSettingsServiceImpl implements EntityGridSettingsService, ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityGridSettingsService.class);

	private static final String ENTITY_GRID_SETTINGS_DICTIONARY_NAME = "entityGridSettingsDictionary";

	private ApplicationContext applicationContext;
	private final int recordsPerPageDefault;
	@Nonnull
	private final List<SortOption> sortOptionsDefault;

	EntityGridSettingsServiceImpl(int recordsPerPageDefault, @Nonnull List<SortOption> sortOptionsDefault) {
		this.recordsPerPageDefault = recordsPerPageDefault;
		this.sortOptionsDefault = sortOptionsDefault;
	}

	@Override
	public void applyGridSettingsFor(@Nonnull Class<?> entityType, @Nonnull EntityGridSettings entityGridSettings) {
		final Map<Class<?>, EntityGridSettings> entityPageSizeDictionary = getEntityGridSettingsDictionary();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\n\nAccept page size:\n\tentityType: {}, gridSettings: {};\n\tdictionary: {}\n", entityType, entityGridSettings, entityPageSizeDictionary);
		}
		if (!Objects.equals(entityGridSettings, entityPageSizeDictionary.get(entityType))) {
			entityPageSizeDictionary.put(entityType, entityGridSettings);
		}

	}

	@Nonnull
	@Override
	public EntityGridSettings obtainEntityGridSettingsFor(@Nonnull Class<?> entityType) {
		final Map<Class<?>, EntityGridSettings> entityPageSizeDictionary = getEntityGridSettingsDictionary();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nGetting page size for {}:\n{}", entityType, entityPageSizeDictionary);
		}
		return ofNullable(entityPageSizeDictionary.get(entityType))
				       .orElseGet(() -> new EntityGridSettings(recordsPerPageDefault, sortOptionsDefault));
	}

	@Override
	public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	private Map<Class<?>, EntityGridSettings> getEntityGridSettingsDictionary() {
		return applicationContext.getBean(ENTITY_GRID_SETTINGS_DICTIONARY_NAME, Map.class);
	}

}
