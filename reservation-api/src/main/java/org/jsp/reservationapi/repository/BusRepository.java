package org.jsp.reservationapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository  extends JpaRepository<Bus, Integer>{
	
	@Query("select b from Bus b where b.bus_number = ?1")
	Optional<Bus> findByBus_Number(String bus_number);
	
	@Query("select b from Bus b where b.from_loc = ?1 and b.to_loc = ?2")
	List<Bus> findByFrom_locAndTo_loc(String from_loc, String to_loc);
	
	@Query("select b from Bus b where b.admin.id = ?1")
	List<Bus> findByAdminId(int id);
	
	@Query("select b from Bus b where b.from_loc=?1 and b.to_loc=?2 and b.dod=?3")
	List<Bus> findBuses(String from_loc, String to_loc, LocalDate dod);
}
