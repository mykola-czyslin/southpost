package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.backup.data.model.MessageKeyHolder;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

@Component
public class MessageKeyHolderLocalizedMapper {

	static final Object[] EMPTY_MESSAGE_PARAMS = new Object[0];
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Nonnull
	public <E extends MessageKeyHolder> AbstractEntityInfo<E> map(@Nonnull E entity, @Nonnull Locale locale) {
		final AbstractEntityInfo<E> entityInfo = new AbstractEntityInfo<>();
		entityInfo.setId(entity);
		entityInfo.setTextValue(messageSource.getMessage(entity.getMessageKey(), EMPTY_MESSAGE_PARAMS, locale));
		return entityInfo;
	}
}
