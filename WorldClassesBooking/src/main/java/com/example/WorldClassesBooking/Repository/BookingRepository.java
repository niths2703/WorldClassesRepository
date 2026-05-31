package com.example.WorldClassesBooking.Repository;

import com.example.WorldClassesBooking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByParentId(Long parentId);

    boolean existsByParentIdAndOfferingId(
            Long parentId,
            Long offeringId);
}
