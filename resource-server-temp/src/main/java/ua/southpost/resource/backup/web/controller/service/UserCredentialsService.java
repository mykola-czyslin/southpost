package ua.southpost.resource.backup.web.controller.service;

public interface UserCredentialsService {
	void updateCredentials(long userId, String oldPassword, String newPassword);
}
