package parkingpos.ticket_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import parkingpos.ticket_service.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    @Query("SELECT t FROM Ticket t WHERE t.plateNumber = :plateNumber AND t.status = 'Active' AND t.deletedAt IS NULL")
    Optional<Ticket> findActiveByPlateNumber(@Param("plateNumber") String plateNumber);
    
    // Better method for existence check - more efficient
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Ticket t WHERE t.plateNumber = :plateNumber AND t.status = 'Active' AND t.deletedAt IS NULL")
    boolean existsActiveByPlateNumber(@Param("plateNumber") String plateNumber);
} 