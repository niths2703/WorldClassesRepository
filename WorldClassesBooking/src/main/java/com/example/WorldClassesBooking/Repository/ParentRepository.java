package com.example.WorldClassesBooking.Repository;

import com.example.WorldClassesBooking.Entity.Parent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    boolean existsByEmailOrMobile(String email, String mobile);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    select p
    from Parent p
    where p.id = :parentId
""")
    Optional<Parent> lockParent(Long parentId);

    Optional<Parent> findByNameAndMobile(
            String name,
            String mobile);

    Optional<Parent> findByName(String name);
}
