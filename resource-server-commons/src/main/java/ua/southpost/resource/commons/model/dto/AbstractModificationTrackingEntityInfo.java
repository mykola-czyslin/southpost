package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractModificationTrackingEntityInfo<I> extends AbstractEntityInfo<I> {
	@JsonProperty("modification_time")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date modificationTime;
	@JsonProperty("modified_by_user")
	private BriefUserInfo modifiedBy;
}
