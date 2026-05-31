package com.example.WorldClassesBooking.Repository;

import com.example.WorldClassesBooking.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByOfferingId(Long offeringId);

    List<Session> findByOfferingIdAndStartTimeAfter(
            Long offeringId,
            Instant currentTime);

    @Query("""
        select count(s) > 0
        from Session s
        where s.offering.id = :offeringId
        and s.startTime < :endTime
        and s.endTime > :startTime
    """)
    boolean existsOverlappingSession(
            Long offeringId,
            Instant startTime,
            Instant endTime);
}
