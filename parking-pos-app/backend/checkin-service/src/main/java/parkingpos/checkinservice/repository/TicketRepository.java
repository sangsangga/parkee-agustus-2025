package parkingpos.checkinservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import parkingpos.checkinservice.dto.projection.TicketProjection;
import parkingpos.checkinservice.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(
        value = "SELECT t.id, t.plate_number as plateNumber, t.check_in_time as checkInTime, t.check_out_time as checkOutTime, t.status as status FROM tickets t WHERE t.plate_number = :plateNumber AND t.check_out_time IS NULL",
        nativeQuery = true
    )
    TicketProjection findActiveByPlateNumber(String plateNumber);
}
