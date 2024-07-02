package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminRequest {
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	@Email(message = "Invalid Format")
	private String email;
	private long phone;
	@NotBlank(message = "GST Number is mandatory")
	@Size(min = 6, max = 10, message = "GST number must have 10 character")
	private String gst_number;
	@NotBlank(message = "Travel Name is mandatory")
	private String travels_name;
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8 , max = 15, message = "Password length must be between 8 and 15")
	private String password;
}
