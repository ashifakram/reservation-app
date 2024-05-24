package org.jsp.reservationapi.dto;

import lombok.Data;

@Data
public class AdminRequest {
	
	private String name;
	private String email;
	private long phone;
	private String gst_number;
	private String travels_name;
	private String password;
}