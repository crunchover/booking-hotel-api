package com.hotel.booking.infrastructure.output.persistence.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelSearchEntityTest {

    private static final LocalDate CHECK_IN = LocalDate.of(2023, 12, 29);
    private static final LocalDate CHECK_OUT = LocalDate.of(2023, 12, 31);

    @Test
    void shouldBuildEntityWithAllFields() {
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .hotelId("hotel1")
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .ages(List.of(30, 25))
                .hash("hash-abc")
                .searchCount(1L)
                .build();

        assertAll(
                () -> assertEquals("hotel1", entity.getHotelId()),
                () -> assertEquals(CHECK_IN, entity.getCheckIn()),
                () -> assertEquals(CHECK_OUT, entity.getCheckOut()),
                () -> assertEquals(List.of(30, 25), entity.getAges()),
                () -> assertEquals("hash-abc", entity.getHash()),
                () -> assertEquals(1L, entity.getSearchCount())
        );
    }

    @Test
    void shouldUseEmptyListWhenAgesIsNull() {
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .hotelId("h1")
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .ages(null)
                .hash("h")
                .searchCount(1L)
                .build();

        assertNotNull(entity.getAges());
        assertTrue(entity.getAges().isEmpty());
    }

    @Test
    void shouldTriggerAuditTimestampsOnPersist() {
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .hotelId("h1").checkIn(CHECK_IN).checkOut(CHECK_OUT)
                .ages(List.of(1)).hash("h").searchCount(1L).build();

        entity.onCreate();
        assertAll(
                () -> assertNotNull(entity.getCreated()),
                () -> assertNotNull(entity.getUpdated())
        );
    }

    @Test
    void shouldUpdateTimestampOnUpdate() {
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .hotelId("h1").checkIn(CHECK_IN).checkOut(CHECK_OUT)
                .ages(List.of(1)).hash("h").searchCount(1L).build();

        entity.onCreate();
        var created = entity.getCreated();
        entity.onUpdate();
        assertNotNull(entity.getUpdated());
        assertEquals(created, entity.getCreated());
    }
}
