package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import ua.southpost.resource.backup.web.model.forms.entity.ClinicForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.ClinicEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class ClinicEntityModelService extends GenericRegionalEntityModelService<ClinicEntityViewModel, ClinicInfo, ClinicForm, Long> {
	@Resource(name = "clinicFormFactory")
	private EntityFormFactory<Long, ClinicInfo, ClinicForm> formFactory;
	@Resource(name = "clinicViewScripts")
	private List<String> viewScripts;
	@Resource(name = "clinicFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected ClinicEntityViewModel createEntityViewModel() {
		return new ClinicEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, ClinicInfo, ClinicForm> formFactory() {
		return this.formFactory;
	}

	@Nonnull
	@Override
	protected List<String> viewScripts() {
		return this.viewScripts;
	}

	@Nonnull
	@Override
	protected List<String> formScripts() {
		return this.formScripts;
	}

}
