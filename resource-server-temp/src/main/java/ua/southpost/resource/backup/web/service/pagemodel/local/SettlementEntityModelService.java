package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.forms.entity.SettlementForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.SettlementEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class SettlementEntityModelService extends GenericRegionalEntityModelService<SettlementEntityViewModel, SettlementInfo, SettlementForm, Long> {
	@Resource(name = "settlementFormFactory")
	private EntityFormFactory<Long, SettlementInfo, SettlementForm> formFactory;
	@Resource(name = "settlementViewScripts")
	private List<String> viewScripts;
	@Resource(name = "settlementFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected SettlementEntityViewModel createEntityViewModel() {
		return new SettlementEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, SettlementInfo, SettlementForm> formFactory() {
		return formFactory;
	}

	@Nonnull
	@Override
	protected List<String> viewScripts() {
		return viewScripts;
	}

	@Nonnull
	@Override
	protected List<String> formScripts() {
		return formScripts;
	}
}
