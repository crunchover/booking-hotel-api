package com.tuempresa.booking.hotel.api.repository;

import com.tuempresa.booking.hotel.api.model.entity.HotelSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository()
public interface HotelSearchRepository extends JpaRepository<HotelSearchEntity, String> {
    Optional<HotelSearchEntity> findByHash(String searchHash);
}
