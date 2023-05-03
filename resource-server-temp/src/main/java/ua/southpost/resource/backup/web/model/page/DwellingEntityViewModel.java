package ua.southpost.resource.backup.web.model.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributeAlias;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributeAliases;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributeMapping;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributesContainer;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ModelAttributesContainer
@ModelAttributeAliases({@ModelAttributeAlias(aliasFor = "entityForm", value = "dwellingForm")})
public class DwellingEntityViewModel extends ExtendedRegionalEntityViewModel<Long> {
	@ModelAttributeMapping(modelAttributeName = ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE)
	public static final String CONTENT_VIEW_PATH = "dwelling/form";
}
