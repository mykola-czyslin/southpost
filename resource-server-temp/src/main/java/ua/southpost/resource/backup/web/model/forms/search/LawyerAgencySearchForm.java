package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LawyerAgencySearchForm  extends SettlementSearchForm {
	@JsonProperty("name_pattern")
	private String namePattern;
	@JsonProperty("web_site_pattern")
	private String webSitePattern;
	@JsonProperty("law_cases")
	private Set<LawCase> lawCaseSet;

	public LawyerAgencySearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public boolean isAnyCase() {
		return ofNullable(lawCaseSet).orElseGet(Collections::emptySet).size() == LawCase.values().length;
	}

	public void setAnyCase(boolean value) {
		if(value) {
			lawCaseSet = Arrays.stream(LawCase.values()).collect(Collectors.toSet());
		}
	}
}
