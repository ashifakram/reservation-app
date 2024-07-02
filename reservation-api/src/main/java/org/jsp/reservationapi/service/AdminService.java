package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private ReservationApiMailService mailService;
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	@Autowired
	private EmailConfiguration emailConfiguration;

	/**
	 * This method will accept {@link AdminRequest(DTO)} and map it to {@link Admin} and by calling
	 * mapToAdmin(AdminRequest).
	 * @param adminRequest
	 * @return {@link ResponseEntity}
	 * @throws ConstraintViolationException if any constraint is violated
	 */
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(AdminRequest adminRequest, HttpServletRequest request) {

		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		
		Admin admin = mapToAdmin(adminRequest);
		
		admin.setStatus(AccountStatus.IN_ACTIVE.toString());
		admin = adminDao.saveAdmin(admin);
		
		String activation_link = linkGeneratorService.getActivationLink(admin, request);
		
		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration.setText("Dear Admin Please Activate Your Account by clicking on the following link:" + activation_link);
		emailConfiguration.setToAddress(admin.getEmail());
		
		structure.setMessage(mailService.sendMail(emailConfiguration));
		structure.setData(mapToAdminResponse(admin));
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
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(AdminRequest adminRequest, int id) {

		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.findAdminById(id);

		if (recAdmin.isPresent()) {

			Admin dbAdmin =  recAdmin.get();
			
			dbAdmin.setName(adminRequest.getName());
			dbAdmin.setEmail(adminRequest.getEmail());
			dbAdmin.setPhone(adminRequest.getPhone());
			dbAdmin.setGst_number(adminRequest.getGst_number());
			dbAdmin.setTravels_name(adminRequest.getTravels_name());
			dbAdmin.setPassword(adminRequest.getPassword());
								
			structure.setMessage("Admin updated");
			structure.setData(mapToAdminResponse(adminDao.saveAdmin(dbAdmin)));  // updating admin
			structure.setStatusCode(HttpStatus.ACCEPTED.value());

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Cannot update Admin as Id is invalid");
		
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdminById(int id) {
		
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.findAdminById(id);
		
		if( recAdmin.isPresent()) {
			
			structure.setData(mapToAdminResponse(recAdmin.get()));
			structure.setMessage("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Admin Id");
	}

	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdminByPhoneAndPassword(long phone, String password) {

		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.verifyAdminByPhoneAndPassword(phone, password);

		if (recAdmin.isPresent()) {
			
			Admin admin = recAdmin.get();
			
			if (admin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Activate Your Account before You Sign In");

			structure.setMessage("Admin Found and Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToAdminResponse(admin));

			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Phone number or password");
	}

	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdminByEmailAndPassword(String email, String password) {

		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();

		Optional<Admin> recAdmin = adminDao.verifyAdminByEmailAndPassword(email, password);

		if (recAdmin.isPresent()) {
			
			Admin admin = recAdmin.get();
			
			if (admin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Activate Your Account before You Sign In");

			structure.setMessage("Admin Found and Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToAdminResponse(admin));

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
	
	public String activate(String token) {
		
		Optional<Admin> recAdmin = adminDao.findByToken(token);
		
		if( recAdmin.isEmpty()) {
			throw new AdminNotFoundException("Invalid Token");
		}
		
		Admin dbAdmin = recAdmin.get();
		dbAdmin.setStatus("ACTIVE");
		dbAdmin.setToken(null);
		
		adminDao.saveAdmin(dbAdmin);
		
		return "Your Account has been activated";
	}
	
	private Admin mapToAdmin(AdminRequest adminRequest) {
		
		return Admin.builder().name(adminRequest.getName()).email(adminRequest.getEmail()).phone(adminRequest.getPhone())
					.gst_number(adminRequest.getGst_number()).travels_name(adminRequest.getTravels_name())
					.password(adminRequest.getPassword()).build();
	}
	
	private AdminResponse mapToAdminResponse(Admin admin) {
		
		return AdminResponse.builder().id(admin.getId()).name(admin.getName()).phone(admin.getPhone()).email(admin.getEmail())
				.gst_number(admin.getGst_number()).travels_name(admin.getTravels_name()).password(admin.getPassword()).build();
	}
	
	public String forgotPassword(String email, HttpServletRequest request) {
		
		Optional<Admin> recAdmin = adminDao.findByEmail(email);
		
		if (recAdmin.isEmpty())
			throw new AdminNotFoundException("Invalid Email Id");
		
		Admin admin = recAdmin.get();
		
		String resetPasswordLink = linkGeneratorService.getResetPasswordLink(admin, request);
		
		emailConfiguration.setToAddress(email);
		emailConfiguration.setText("Please click on the following link to reset your password " + resetPasswordLink);
		emailConfiguration.setSubject("RESET YOUR PASSWORD");
		
		mailService.sendMail(emailConfiguration);
		
		return "reset password link has been sent to entered mail Id";
	}

	public AdminResponse verifyLink(String token) {
		
		Optional<Admin> recAdmin = adminDao.findByToken(token);
		
		if (recAdmin.isEmpty())
			throw new AdminNotFoundException("Link has been expired or it is Invalid");
		
		Admin dbAdmin = recAdmin.get();
		
		dbAdmin.setToken(null);
		adminDao.saveAdmin(dbAdmin);
		
		return mapToAdminResponse(dbAdmin);
	}

}
