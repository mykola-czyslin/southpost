/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.data.repo.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The REST controller that manages user management requests.
 * Created by mchys on 14.04.2018.
 */
@RestController
public class UserManagementEndpoint {
	@Resource
	private UserRepository repository;

	@DeleteMapping("/api/user/admin/{user_id}/delete")
	public void delete(@PathVariable(name = "user_id") long userId) {
		repository.findById(userId).filter(u -> !u.isInternalUser()).ifPresent(repository::delete);
	}

}
