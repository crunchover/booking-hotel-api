package com.tuempresa.booking.hotel.api.model.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "hotel_searches")
public class HotelSearchEntity extends AbstractRecordMetadataModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_id", nullable = false)
    private String hotelId;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @ElementCollection
    @CollectionTable(name = "hotel_search_ages", joinColumns = @JoinColumn(name = "search_id"))
    @Column(name = "age")
    private List<Integer> ages;

    @Column(nullable = false, unique = true, length = 100)
    private String hash;

    @Column(name = "search_count", nullable = false)
    private Long searchCount;

    protected HotelSearchEntity() {
        // JPA only
    }

    public HotelSearchEntity(Long id, String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages, String hash, Long searchCount) {
        this.id = id;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
        this.hash = hash;
        this.searchCount = searchCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }

    public void setAges(List<Integer> ages) {
        this.ages = ages;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Long qty) {
        this.searchCount = qty;
    }
    public static class Builder {
        private Long id;
        private String searchId;
        private String hotelId;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private List<Integer> ages;
        private String hash;
        private Long searchCount;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder searchId(String searchId) {
            this.searchId = searchId;
            return this;
        }

        public Builder hotelId(String hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder checkIn(LocalDate checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public Builder checkOut(LocalDate checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public Builder ages(List<Integer> ages) {
            this.ages = ages;
            return this;
        }

        public Builder hash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder searchCount(Long searchCount) {
            this.searchCount = searchCount;
            return this;
        }

        public HotelSearchEntity build() {
            return new HotelSearchEntity(
                    id,
                    hotelId,
                    checkIn,
                    checkOut,
                    ages,
                    hash,
                    searchCount
            );
        }
    }

}
