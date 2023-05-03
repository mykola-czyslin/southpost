package ua.southpost.resource.backup.web.model.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.web.controller.page.ModelAttributeConstants;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributeMapping;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributesContainer;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ModelAttributesContainer
public class RegionalEntityViewModel<I> extends EntityViewModel<I> {
	@ModelAttributeMapping(modelAttributeName = ModelAttributeConstants.REGION_OPTIONS_ATTRIBUTE_NAME)
	private Map<String,String> regionOptions;
	@ModelAttributeMapping(modelAttributeName = ModelAttributeConstants.DISTRICT_OPTIONS_ATTRIBUTE_NAME)
	private Map<String,String> districtOptions;

}
