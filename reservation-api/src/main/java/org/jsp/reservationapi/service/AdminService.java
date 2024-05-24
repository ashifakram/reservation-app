package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	/**
	 * This method will accept {@link AdminRequest(DTO)} and map it to {@link Admin} and by calling
	 * mapToAdmin(AdminRequest).
	 * @param adminRequest
	 * @return {@link ResponseEntity}
	 * @throws ConstraintViolationException if any constraint is violated
	 */
	
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(AdminRequest adminRequest) {

		ResponseStructure<Admin> structure = new ResponseStructure<>();

		structure.setMessage("Admin saved");
		structure.setData(adminDao.saveAdmin(mapToAdmin(adminRequest)));
		structure.setStatusCode(HttpStatus.CREATED.value());

		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}
	
	/**
	 * This method will accept AdminRequest(DTO) and Admin Id and update the Admin
	 * in the database if Identifier is valid 
	 * 
	 * @param AdminRequest
	 * @param id
	 * @return {@link ResponseEntity }
	 * @throws {@code AdminNotFoundException} if Identifier is invalid
	 */
	
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(AdminRequest adminRequest, int id) {

		ResponseStructure<Admin> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.findAdminById(id);

		if (recAdmin.isPresent()) {

			Admin dbAdmin =  mapToAdmin(adminRequest); // recAdmin.get();
					
			dbAdmin.setId(id);
					
			structure.setMessage("Admin updated");
			structure.setData(adminDao.saveAdmin(dbAdmin));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Cannot update Admin as Id is invalid");
		
	}
	
	public ResponseEntity<ResponseStructure<Admin>> findAdminById(int id) {
		
		ResponseStructure<Admin> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.findAdminById(id);
		
		if( recAdmin.isPresent()) {
			
			structure.setData(recAdmin.get());
			structure.setMessage("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Admin Id");
	}

	public ResponseEntity<ResponseStructure<Admin>> verifyAdminByPhoneAndPassword(long phone, String password) {

		ResponseStructure<Admin> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.verifyAdminByPhoneAndPassword(phone, password);

		if (recAdmin.isPresent()) {

			structure.setMessage("Admin Found and Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(recAdmin.get());

			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Phone number or password");
	}

	public ResponseEntity<ResponseStructure<Admin>> verifyAdminByEmailAndPassword(String email, String password) {

		ResponseStructure<Admin> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.verifyAdminByEmailAndPassword(email, password);

		if (recAdmin.isPresent()) {

			structure.setMessage("Admin Found and Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(recAdmin.get());

			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Email id or password");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteAdminById(int id) {
		
		ResponseStructure<String> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.findAdminById(id);
		
		if( recAdmin.isPresent()) {
			
			adminDao.deleteAdminById(id);
			
			structure.setMessage("Admin deleted succesfully");
			structure.setData("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Cannot delete Admin as Id is Invalid");
		
	}
	
	private Admin mapToAdmin(AdminRequest adminRequest) {
		
			return Admin.builder().name(adminRequest.getName()).email(adminRequest.getEmail()).phone(adminRequest.getPhone())
					.gst_number(adminRequest.getGst_number()).travels_name(adminRequest.getTravels_name())
					.password(adminRequest.getPassword()).build();
	}

}
