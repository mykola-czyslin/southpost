package ua.southpost.resource.backup.api.service;

import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManager;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.history.EntityHistoryService;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import ua.southpost.resource.commons.service.LocalizedEntityMapper;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

class GenericEntityManagementRequestHandler<I, T extends EntityInfo<I>, P extends EntityPayload<I>, E extends Identity<I>> implements EntityManagementRequestHandler<I, T, P> {
	private static final String ERR_NOT_AUTHENTICATED_KEY = "err.not.authenticated";
	private static final String ERR_NOT_AUTHENTICATED_DEFAULT = "Session isn't authenticated";

	@Nonnull
	private final EntityPayloadPersistenceManager<I, E, P> persistenceManager;
	@Nonnull
	private final LocalizedEntityMapper<E, T, I> entityInfoMapper;
	@Nonnull
	private final UserRepository userRepository;
	@Nonnull
	private final MessageSource messageSource;
	@Nonnull
	private final EntityHistoryService entityHistoryService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	GenericEntityManagementRequestHandler(@Nonnull EntityPayloadPersistenceManager<I, E, P> persistenceManager, @Nonnull LocalizedEntityMapper<E, T, I> entityInfoMapper, @Nonnull UserRepository userRepository, @Nonnull MessageSource messageSource, @Nonnull EntityHistoryService entityHistoryService) {
		this.persistenceManager = persistenceManager;
		this.entityInfoMapper = entityInfoMapper;
		this.userRepository = userRepository;
		this.messageSource = messageSource;
		this.entityHistoryService = entityHistoryService;
	}


	@Override
	public AbstractSubmitResponse<I, T> handleSubmitRequest(@Nonnull HttpServletRequest servletRequest, @Nonnull AbstractSubmitRequest<P, I> requestPayload, @Nonnull BindingResult bindingResult) {
		final AbstractSubmitResponse<I, T> responsePayload = new AbstractSubmitResponse<>();
		if (bindingResult.hasErrors()) {
			responsePayload.setValidationErrors(
					bindingResult.getAllErrors().stream()
							.map(o -> ValidationError.fromObjectError(o, messageSource, servletRequest.getLocale()))
							.collect(Collectors.toList())
			);
		} else {
			final User sessionUser = this.resolveSessionUser(servletRequest);
			logger.debug("Entity Form: {}", requestPayload.getData());
			final E entity = persistenceManager.persist(requestPayload.getData(), requestPayload.getAction().isMerging(), sessionUser);
			logger.debug(
					"The {} entity has been just {} by {} user:\n{}",
					entity.getClass().getSimpleName(),
					requestPayload.getAction(),
					sessionUser.displayValue(),
					entity
			);
			entityHistoryService.add(entity, requestPayload.getAction().getChangeType(), sessionUser);
			responsePayload.setSucceeded(true);

			responsePayload.setEntityInfo(entityInfoMapper.map(entity, servletRequest.getLocale()));
		}
		return responsePayload;
	}

	@Override
	public void handleRemoveRequest(@Nonnull HttpServletRequest servletRequest, I entityId) {
		final User sessionUser = this.resolveSessionUser(servletRequest);
		E entity = persistenceManager.remove(entityId);
		entityHistoryService.add(entity, ChangeType.DELETE, sessionUser);
		logger.debug(
				String.format(
						"The %s user going to remove %s entity with ID = %s",
						sessionUser.displayValue(), entity.getClass().getSimpleName(), entityId
				)
		);
	}

	private User resolveSessionUser(HttpServletRequest servletRequest) {
		final String userName = Optional.ofNullable(servletRequest.getUserPrincipal())
				.map(Principal::getName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		return userRepository.findByLogin(userName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, messageSource.getMessage(ERR_NOT_AUTHENTICATED_KEY, new Object[]{userName}, ERR_NOT_AUTHENTICATED_DEFAULT, servletRequest.getLocale())));
	}

}
