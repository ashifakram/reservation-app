package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.exception.UserNotFoundException;
import org.jsp.reservationapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * This method will accept {@link UserRequest} and maps it to {@link User} and by calling 
	 * mapToAdmin(UserRequest).
	 * @param userRequest
	 * @return {@link ResponseEntity}
	 * @throws ConstraintViolationException if any constraint is violated
	 */
	public ResponseEntity<ResponseStructure<User>> saveUser(UserRequest userRequest) {

		ResponseStructure<User> structure = new ResponseStructure<>();

		structure.setMessage("User saved");
		structure.setData(userDao.saveUser(mapToUser(userRequest)));
		structure.setStatusCode(HttpStatus.CREATED.value());

		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}

	/**
	 * This method will accept UserRequest(DTO) and User Id and update the User
	 * in the Database if identifier is valid.
	 * @param userRequest
	 * @param id          
	 * @return {@link ResponseEntity}
	 * @throws {@code UserNotFoundException} if Identifier is Invalid
	 */
	public ResponseEntity<ResponseStructure<User>> updateUser(UserRequest userRequest, int id) {

		ResponseStructure<User> structure = new ResponseStructure<>();

		Optional<User> recUser = userDao.findUserById(id);

		if (recUser.isPresent()) {

			User dbUser = mapToUser(userRequest);
			
			dbUser.setId(id);
			
			structure.setMessage("User updated");
			structure.setData(userDao.saveUser(dbUser));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}

		throw new UserNotFoundException("Cannot update User as Id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<User>> findUserById(int id) {
		
		ResponseStructure<User> structure = new ResponseStructure<>();

		Optional<User> recUser = userDao.findUserById(id);
		
		if( recUser.isPresent()) {
			
			structure.setData(recUser.get());
			structure.setMessage("User Found");
			structure.setStatusCode(HttpStatus.OK.value());
			
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid User Id");
	}

	public ResponseEntity<ResponseStructure<User>> verifyUserByPhoneAndPassword(long phone, String password) {

		ResponseStructure<User> structure = new ResponseStructure<>();

		Optional<User> recUser = userDao.verifyUserByPhoneAndPassword(phone, password);

		if (recUser.isPresent()) {

			structure.setMessage("User Found and Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(recUser.get());

			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid Phone number or password");
	}

	public ResponseEntity<ResponseStructure<User>> verifyUserByEmailAndPassword(String email, String password) {

		ResponseStructure<User> structure = new ResponseStructure<>();

		Optional<User> recUser = userDao.verifyUserByEmailAndPassword(email, password);

		if (recUser.isPresent()) {

			structure.setMessage("User Found and Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(recUser.get());

			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid Email id or password");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteUserById(int id) {
		
		ResponseStructure<String> structure = new ResponseStructure<>();

		Optional<User> recUser = userDao.findUserById(id);
		
		if( recUser.isPresent()) {
			
			userDao.deleteUserById(id);
			
			structure.setMessage("User deleted succesfully");
			structure.setData("User Found");
			structure.setStatusCode(HttpStatus.OK.value());
			
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Cannot delete User as Id is Invalid");
		
	}
	
	private User mapToUser(UserRequest userRequest) {
		
		return User.builder().name(userRequest.getName()).phone(userRequest.getPhone()).email(userRequest.getEmail())
				.gender(userRequest.getGender()).age(userRequest.getAge()).password(userRequest.getPassword()).build();
	}
}
