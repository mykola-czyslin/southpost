package ua.southpost.resource.backup.web.security;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static ua.southpost.resource.backup.web.security.SecurityUtils.ROLE_NAME_VIEWER;
import static ua.southpost.resource.backup.web.security.SecurityUtils.mapRolesToAuthorities;

/**
 * implementation of {@link UserDetailsService}.
 * Created by mchys on 13.11.2016.
 */
public class UserInfoService implements UserDetailsService {
	private final UserRepository userRepository;


	public UserInfoService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' isn't registered", username)));
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(user.getLogin());
		userInfo.setPassword(user.getPassword());
		userInfo.setAuthorities(loadUserRoles(user));
		return userInfo;
	}

	private List<GrantedAuthority> loadUserRoles(User user) {
		ArrayList<GrantedAuthority> result = new ArrayList<>();
		result.add(new SimpleGrantedAuthority(ROLE_NAME_VIEWER));
		result.addAll(
				mapRolesToAuthorities(DomainRole.fromUser(user))
		);
		return result;
	}
}
