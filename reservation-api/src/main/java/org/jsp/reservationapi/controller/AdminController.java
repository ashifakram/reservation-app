package org.jsp.reservationapi.controller;

import java.io.IOException;

import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(@Valid @RequestBody AdminRequest adminRequest, HttpServletRequest request) {
		
		return adminService.saveAdmin(adminRequest, request);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@RequestBody AdminRequest adminRequest, @PathVariable int id) {
		
		return adminService.updateAdmin(adminRequest, id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdminById(@PathVariable int id) {
		
		return adminService.findAdminById(id);
	}
	
	@PostMapping("/verify-by-phone-and-password")
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdminByPhoneAndPassword(@RequestParam(name = "phone") long phone, @RequestParam(name = "password") String password) {
		
		return adminService.verifyAdminByPhoneAndPassword(phone, password);
	}
	
	@PostMapping("/verify-by-email-and-password")
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdminByEmailAndPassword(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
		
		return adminService.verifyAdminByEmailAndPassword(email, password);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAdminById(@PathVariable int id) {
		
		return adminService.deleteAdminById(id);
	}
	
	@GetMapping("/activate")
	public String sendMail(@RequestParam String token) {
		
		return adminService.activate(token);
	}
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email, HttpServletRequest request) {
		return adminService.forgotPassword(email, request);
	}

	@GetMapping("/verify-link")
	public void verifyResetPasswordLink(@RequestParam String token, HttpServletRequest request,
			HttpServletResponse response) {
		AdminResponse adminResponse = adminService.verifyLink(token);

		if (adminResponse != null)
			try {
				HttpSession session = request.getSession();
				session.setAttribute("admin", adminResponse);
						response.addCookie(new Cookie("admin", adminResponse.getEmail()));
				response.sendRedirect("http://localhost:3000/reset-password");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}
