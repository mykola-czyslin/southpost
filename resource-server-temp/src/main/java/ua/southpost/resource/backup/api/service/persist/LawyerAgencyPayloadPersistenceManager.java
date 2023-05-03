package ua.southpost.resource.backup.api.service.persist;

import com.google.common.collect.Sets;
import ua.southpost.resource.backup.api.model.submit.LawyerAgencyPayload;
import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.LawyerAgencyRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static ua.southpost.resource.backup.service.NotFoundException.ERR_LAWYER_AGENCY_NOT_FOUND_BY_ID;

@Component
@NotFoundMessage(key = ERR_LAWYER_AGENCY_NOT_FOUND_BY_ID)
class LawyerAgencyPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, LawyerAgency, LawyerAgencyPayload, LawyerAgencyRepository> {

	@Resource
	private LawyerAgencyRepository repository;
	@Resource
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Resource
	private ContactPayloadPersistenceManager contactPersistenceManager;

	@Override
	protected LawyerAgencyRepository getRepository() {
		return repository;
	}

	@Override
	protected LawyerAgency createEntityInstance() {
		return new LawyerAgency();
	}

	@Override
	protected void populateEntity(LawyerAgencyPayload payload, LawyerAgency entity, boolean merge, User modifiedBy) {
		entity.setSettlement(settlementPersistenceManager.persist(payload.getSettlement(), merge, modifiedBy));
		entity.setDisplayName(payload.getAgencyName());
		entity.setSearchValue(payload.getAgencyName());
		entity.setWebSite(payload.getWebSite());
		entity.setCases(Sets.newEnumSet(ofNullable(payload.getCases()).orElseGet(Collections::emptyList), LawCase.class));
		contactPersistenceManager.populateEntity(payload.getContact(), entity, merge, modifiedBy);
		entity.setModifiedBy(modifiedBy);
		entity.setModificationTime(new Date());
	}

	@Override
	protected Optional<LawyerAgency> findUnique(LawyerAgency entity) {
		return repository.findByName(entity.getSearchValue());
	}

	@Nonnull
	@Override
	protected LawyerAgency merge(@Nonnull LawyerAgency from, @Nonnull LawyerAgency to) {
		to.setDisplayName(from.getDisplayName());
		to.setSearchValue(from.getSearchValue());
		to.setWebSite(from.getWebSite());
		to.setSettlement(settlementPersistenceManager.merge(from.getSettlement(), to.getSettlement()));
		contactPersistenceManager.merge(from, to);
		to.setCases(from.getCases());
		to.setModifiedBy(from.getModifiedBy());
		to.setModificationTime(from.getModificationTime());
		return to;
	}
}
