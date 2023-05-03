package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class SettlementSearchFormFactory implements SearchFormFactory<SettlementSearchForm> {
	@Nonnull
	@Override
	public SettlementSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new SettlementSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
