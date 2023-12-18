package com.fpt.security.jwt;

import com.fpt.security.dto.LoginPhoneRequest;
import com.fpt.security.dto.LoginRequest;
import com.fpt.security.dto.LoginResponse;
import com.fpt.repository.UserRepository;
import com.fpt.security.mapper.UserMapper;
import com.fpt.security.service.UserService;
import com.fpt.model.User;
import com.fpt.security.dto.AuthenticatedUserDto;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

	private final UserService userService;

	private final JwtTokenManager jwtTokenManager;

	private final UserRepository userRepository;

	private final AuthenticationManager authenticationManager;

	public LoginResponse getLoginResponse(LoginRequest loginRequest) {

		final String username = loginRequest.getUsername();
		final String password = loginRequest.getPassword();

		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

		final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);
		final String token = jwtTokenManager.generateToken(user);

		log.info("{} has successfully logged in!", user.getUsername());

		return new LoginResponse(token, user.getUserRole(), user.getUsername());
	}

	public LoginResponse getLoginPhoneResponse(LoginPhoneRequest loginPhoneRequest) {
		try {
			FirebaseToken isValid = jwtTokenManager.verifyIdToken(loginPhoneRequest.getIdToken());

			if(isValid != null) {
				String phoneNumber = isValid.getClaims().get("phone_number").toString();
				phoneNumber = phoneNumber.replaceAll("\\s+", "").replaceAll("\\+", "");
				if (phoneNumber.startsWith("84")) {
					phoneNumber = "0" + phoneNumber.substring(2);
				}

				User user = userService.findByPhoneNumber(phoneNumber);
				final String token = jwtTokenManager.generateToken(user);

				log.info("{} has successfully logged in!", user.getUsername());

				return new LoginResponse(token, user.getUserRole(), user.getUsername());
			}
		} catch (FirebaseAuthException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
