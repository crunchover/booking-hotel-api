package com.hotel.booking.infrastructure.output.persistence.repository;

import com.hotel.booking.infrastructure.output.persistence.entity.HotelSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelSearchJpaRepository extends JpaRepository<HotelSearchEntity, Long> {

    Optional<HotelSearchEntity> findByHash(String hash);

    @Modifying
    @Query("UPDATE HotelSearchEntity s SET s.searchCount = s.searchCount + 1 WHERE s.hash = :hash")
    void incrementSearchCount(@Param("hash") String hash);
}
