package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.ClinicSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.ClinicPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/api/clinic")
public class ClinicManagementEndpoint {
	@Resource(name = "clinicEntityLookupService")
	private EntityLookupService<ClinicSearchRequestPayload, ClinicInfo, Long> lookupService;
	@Resource(name = "clinicEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, ClinicInfo, ClinicPayload> entityManagementRequestHandler;

	@PostMapping(value = "/lookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LookupResponse<ClinicInfo> lookup(@RequestBody ClinicSearchRequestPayload searchPayload, Locale locale) {
		return lookupService.lookup(searchPayload, locale);
	}

	@PutMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, ClinicInfo> save(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<ClinicPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping("/{clinicId}/delete")
	public void remove(HttpServletRequest request, @PathVariable("clinicId") long clinicId) {
		entityManagementRequestHandler.handleRemoveRequest(request, clinicId);
	}

}
