package com.x.hotelmanagement.repository;

import com.x.hotelmanagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Belirli bir odanın belirtilen tarihlerde rezervasyonu olup olmadığını kontrol eden sorgu
    List<Booking> findByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(Long roomId, LocalDate checkOut, LocalDate checkIn);
}
