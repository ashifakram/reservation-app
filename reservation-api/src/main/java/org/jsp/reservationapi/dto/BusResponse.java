package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusResponse {
	
	private int id;
	private String name;
	private String bus_number;
	private int no_of_seats;
	private LocalDate dod;
	private String from_loc;
	private String to_loc;
}
