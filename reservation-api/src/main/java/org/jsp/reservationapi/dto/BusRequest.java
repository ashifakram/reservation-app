package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusRequest {
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotBlank(message = "Bus number is mandatory")
	private String bus_number;
	private int no_of_seats;
	private LocalDate dod;
	@NotBlank(message = "from loc is mandatory")
	private String from_loc;
	@NotBlank(message = "To loc is mandatory")
	private String to_loc;
	private double costPerSeat;
}
