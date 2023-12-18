package com.fpt.security.service;

import com.fpt.security.dto.RegistrationRequest;
import com.fpt.security.dto.RegistrationResponse;
import com.fpt.dto.PasswordRequest;
import com.fpt.dto.UserDto;
import com.fpt.model.User;
import com.fpt.model.UserRole;
import com.fpt.security.dto.AuthenticatedUserDto;

import java.util.List;

public interface UserService {
	User findByUsername(String username);
	List<User> listAllUser();
	boolean deleteUserByUserName(String username);
	User updateUserByUserName(String username, UserDto userDto);
	User updateUserProfile(String token, UserDto userDto);
	User changePassword(String token, PasswordRequest request);
	RegistrationResponse registration(RegistrationRequest registrationRequest);
	RegistrationResponse createUserByAdmin(RegistrationRequest registrationRequest, UserRole userRole);
	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);
	boolean sendPasswordToEmail(String email);
	User findByPhoneNumber(String phoneNumber);
}
