package ua.southpost.resource.backup.web.service;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RegionAndDistrictOptionsContainer {
	public static final String MOCK_REGION_ID = "_";

	private final Map<String,String> regionOptions;
	private final Map<String,String> districtOptions;
}
