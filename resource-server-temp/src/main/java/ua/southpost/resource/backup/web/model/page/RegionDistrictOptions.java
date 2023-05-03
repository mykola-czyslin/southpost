package ua.southpost.resource.backup.web.model.page;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class RegionDistrictOptions {
	@Nonnull
	private final Map<String, Map<String, String>> regionDistrictOptions;

	public RegionDistrictOptions(@Nonnull Map<String, Map<String, String>> regionDistrictOptions) {
		this.regionDistrictOptions = regionDistrictOptions;
	}

	public Map<String,String> districtOptions(String regionId) {
		return this.regionDistrictOptions.getOrDefault(regionId, Collections.emptyMap());
	}
}
