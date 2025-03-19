package com.x.hotelmanagement.service;

import com.x.hotelmanagement.entity.Booking;
import com.x.hotelmanagement.entity.Room;
import com.x.hotelmanagement.repository.BookingRepository;
import com.x.hotelmanagement.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    /**
     * Yeni rezervasyon oluşturur.
     * @param booking Yeni rezervasyon nesnesi
     * @return Booking oluşturulan rezervasyon
     */
    @Transactional
    public Booking createBooking(Booking booking) {
        if (booking.getRoom() == null || booking.getRoom().getId() == null) {
            throw new RuntimeException("Room ID cannot be null");
        }

        // Odanın var olup olmadığını kontrol et
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + booking.getRoom().getId()));

        // Oda belirtilen tarihlerde müsait mi kontrol et
        List<Booking> existingBookings = bookingRepository.findByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(
                room.getId(), booking.getCheckOutDate(), booking.getCheckInDate()
        );

        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        // Yeni rezervasyonu kaydet
        booking.setRoom(room);
        booking.setConfirmed(false);
        return bookingRepository.save(booking);
    }
    //**************************************************************************************************************
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    //**************************************************************************************************************
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }
    //**************************************************************************************************************
    @Transactional
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        Room room = roomRepository.findById(existingBooking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found for this booking"));

        List<Booking> overlappingBookings = bookingRepository.findByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(
                room.getId(), updatedBooking.getCheckOutDate(), updatedBooking.getCheckInDate()
        );

        if (!overlappingBookings.isEmpty() && overlappingBookings.stream().anyMatch(b -> !b.getId().equals(id))) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        existingBooking.setCustomerName(updatedBooking.getCustomerName());
        existingBooking.setCustomerEmail(updatedBooking.getCustomerEmail());
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setConfirmed(updatedBooking.isConfirmed());

        return bookingRepository.save(existingBooking);
    }
    //**************************************************************************************************************
    @Transactional
    public String deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found for this booking"));

        bookingRepository.delete(booking);
        return "Booking with ID " + id + " has been successfully deleted.";
    }

    //**************************************************************************************************************
}
