package ua.southpost.resource.backup.web.model.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.web.controller.page.ModelAttributeConstants;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.forms.entity.EntityForm;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributeMapping;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributesContainer;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@ModelAttributesContainer
public class EntityViewModel<I> {
	@ModelAttributeMapping(modelAttributeName = ModelAttributeConstants.ENTITY_FORM)
	private EntityForm<I> entityForm;
	@ModelAttributeMapping(modelAttributeName = ModelAttributeConstants.SUBMIT_TYPE_ATTRIBUTE_NAME)
	private SubmitType submitType;
	@ModelAttributeMapping(modelAttributeName = ModelAttributeConstants.SCRIPTS_ATTRIBUTE_NAME)
	private List<String> scripts;
}
