package ua.southpost.resource.backup.web.model.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributeMapping;
import ua.southpost.resource.backup.web.service.pagemodel.ModelAttributesContainer;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ModelAttributesContainer
public class ExtendedRegionalEntityViewModel<I> extends RegionalEntityViewModel<I> {
	@ModelAttributeMapping(modelAttributeName = "regionDistrictOptions")
	private RegionDistrictOptions regionDistrictOptions;
}
