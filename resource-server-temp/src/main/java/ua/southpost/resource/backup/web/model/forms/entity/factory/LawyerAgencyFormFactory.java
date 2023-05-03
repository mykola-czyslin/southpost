package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo;
import ua.southpost.resource.backup.web.model.forms.entity.LawyerAgencyForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
class LawyerAgencyFormFactory extends GenericEntityFormFactory<Long, LawyerAgencyInfo, LawyerAgencyForm> {
	@Resource(name = "lawyerAgencyEntityDataService")
	private EntityDataService<LawyerAgencyInfo, Long> entityDataService;
	@Resource
	private SettlementFormFactory settlementFormFactory;
	@Resource
	private ContactFormFactory contactFormFactory;

	@Override
	protected EntityDataService<LawyerAgencyInfo, Long> getDataService() {
		return entityDataService;
	}

	@Nonnull
	@Override
	public LawyerAgencyForm createNewFormInstance() {
		return new LawyerAgencyForm();
	}

	@Nonnull
	@Override
	public LawyerAgencyForm formFromEntity(@Nonnull LawyerAgencyInfo entity) {
		LawyerAgencyForm form = new LawyerAgencyForm();
		form.setAgencyName(entity.getAgencyName());
		form.setWebSite(entity.getWebSite());
		form.setSettlementForm(settlementFormFactory.formFromEntity(entity.getSettlement()));
		form.setContact(contactFormFactory.formFromEntity(entity));
		form.setCases(
				ofNullable(entity.getCases()).orElseGet(Collections::emptySet)
					.stream()
					.map(AbstractEntityInfo::getId)
					.map(Enum::name)
					.collect(Collectors.toList())
		);
		return form;
	}
}
