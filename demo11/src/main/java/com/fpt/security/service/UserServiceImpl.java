package com.fpt.security.service;

import com.fpt.security.dto.RegistrationRequest;
import com.fpt.security.dto.RegistrationResponse;
import com.fpt.security.jwt.JwtTokenManager;
import com.fpt.dto.PasswordRequest;
import com.fpt.dto.UserDto;
import com.fpt.repository.UserRepository;
import com.fpt.model.User;
import com.fpt.model.UserRole;
import com.fpt.security.dto.AuthenticatedUserDto;
import com.fpt.security.mapper.UserMapper;
import com.fpt.service.UserValidationService;
import com.fpt.utils.GeneralMessageAccessor;
import com.fpt.utils.RandomPasswordGenerator;
import com.fpt.utils.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";
	private final JwtTokenManager jwtTokenManager;
	private final UserRepository userRepository;
	private final SendMail sendMail;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserValidationService userValidationService;
	private final GeneralMessageAccessor generalMessageAccessor;

	public UserServiceImpl(JwtTokenManager jwtTokenManager, UserRepository userRepository, SendMail sendMail, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidationService userValidationService, GeneralMessageAccessor generalMessageAccessor) {
		this.jwtTokenManager = jwtTokenManager;
		this.userRepository = userRepository;
		this.sendMail = sendMail;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userValidationService = userValidationService;
		this.generalMessageAccessor = generalMessageAccessor;
	}

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> listAllUser() {
		return userRepository.findAll();
	}

	@Override
	public boolean deleteUserByUserName(String username) {
		User user = userRepository.findByUsername(username);
		if(user!= null) {
			userRepository.delete(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User updateUserByUserName(String username, UserDto userDto) {
		User u = userRepository.findByUsername(username);
		if(u != null){
			u.setName(userDto.getName());
			u.setDateOfBirth(userDto.getDateOfBirth());
			u.setPhone(userDto.getPhone());
			u.setAddress(userDto.getAddress());

			if(userDto.getPassword() != null){
				u.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			}

			if(userDto.getUserRole().equals(UserRole.ROLE_ADMIN.name())) {
				u.setUserRole(UserRole.ROLE_ADMIN);
			}
			if(userDto.getUserRole().equals(UserRole.ROLE_BLOG.name())) {
				u.setUserRole(UserRole.ROLE_BLOG);
			}
			if(userDto.getUserRole().equals(UserRole.ROLE_PRODUCT.name())) {
				u.setUserRole(UserRole.ROLE_PRODUCT);
			}
			if(userDto.getUserRole().equals(UserRole.ROLE_USER.name())) {
				u.setUserRole(UserRole.ROLE_USER);
			}
			userRepository.save(u);
			return u;
		}
		return null;
	}

	@Override
	public User updateUserProfile(String token, UserDto userDto) {
		String username = jwtTokenManager.getUsernameFromToken(token);
		User u = userRepository.findByUsername(userDto.getUsername());
		if(username.equals(u.getUsername())){
			u.setName(userDto.getName());
			u.setImage(userDto.getImage());
			u.setDateOfBirth(userDto.getDateOfBirth());
			u.setAddress(userDto.getAddress());
			userRepository.save(u);
			return u;
		}
		return null;
	}

	public User changePassword(String token, PasswordRequest request) {

		String username = jwtTokenManager.getUsernameFromToken(token);

		User u = userRepository.findByUsername(username);
		if(u != null){
			u.setPassword(bCryptPasswordEncoder.encode(request.getNewpass()));
			userRepository.save(u);
			return u;
		}
		return null;
	}

	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(UserRole.ROLE_USER);

		userRepository.save(user);

		final String username = registrationRequest.getUsername();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

		log.info("{} registered successfully!", username);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public RegistrationResponse createUserByAdmin(RegistrationRequest registrationRequest, UserRole userRole) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(userRole);

		userRepository.save(user);

		final String username = registrationRequest.getUsername();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

		log.info("{} registered successfully!", username);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}

	@Override
	public boolean sendPasswordToEmail(String email) {
		try {
			User u = userRepository.findUserByEmail(email);
			RandomPasswordGenerator rpg = new RandomPasswordGenerator();
			String pass = rpg.generateRandomPassword();
			u.setPassword(bCryptPasswordEncoder.encode(pass));
			userRepository.save(u);
			String bodyEmail = "Mật khẩu mới cho tài khoản " + u.getUsername() + " là: " + pass;
			sendMail.sendMailRender(u.getEmail(), "PASSWORD RECOVER", bodyEmail);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	@Override
	public User findByPhoneNumber(String phoneNumber) {
		return userRepository.findUserByPhone(phoneNumber);
	}
}
