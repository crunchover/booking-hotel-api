package com.hotel.booking.infrastructure.output.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "hotel_searches")
public class HotelSearchEntity extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_id", nullable = false)
    private final String hotelId;

    @Column(name = "check_in", nullable = false)
    private final LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private final LocalDate checkOut;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hotel_search_ages", joinColumns = @JoinColumn(name = "search_id"))
    @OrderColumn(name = "age_order")
    @Column(name = "age")
    private final List<Integer> ages;

    @Column(nullable = false, unique = true, length = 100)
    private final String hash;

    @Column(name = "search_count", nullable = false)
    private Long searchCount;

    protected HotelSearchEntity() {
        this.hotelId = null;
        this.checkIn = null;
        this.checkOut = null;
        this.ages = null;
        this.hash = null;
        this.searchCount = null;
    }

    private HotelSearchEntity(Builder builder) {
        this.hotelId = builder.hotelId;
        this.checkIn = builder.checkIn;
        this.checkOut = builder.checkOut;
        this.ages = builder.ages != null ? List.copyOf(builder.ages) : List.of();
        this.hash = builder.hash;
        this.searchCount = builder.searchCount;
    }

    public Long getId() { return id; }
    public String getHotelId() { return hotelId; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public List<Integer> getAges() { return ages; }
    public String getHash() { return hash; }
    public Long getSearchCount() { return searchCount; }

    public static class Builder {
        private String hotelId;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private List<Integer> ages;
        private String hash;
        private Long searchCount;

        public Builder hotelId(String hotelId) { this.hotelId = hotelId; return this; }
        public Builder checkIn(LocalDate checkIn) { this.checkIn = checkIn; return this; }
        public Builder checkOut(LocalDate checkOut) { this.checkOut = checkOut; return this; }
        public Builder ages(List<Integer> ages) { this.ages = ages; return this; }
        public Builder hash(String hash) { this.hash = hash; return this; }
        public Builder searchCount(Long searchCount) { this.searchCount = searchCount; return this; }
        public HotelSearchEntity build() { return new HotelSearchEntity(this); }
    }
}
