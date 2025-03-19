package com.x.hotelmanagement.service;

import com.x.hotelmanagement.entity.Booking;
import com.x.hotelmanagement.entity.Room;
import com.x.hotelmanagement.repository.BookingRepository;
import com.x.hotelmanagement.repository.RoomRepository;
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
    public Booking createBooking(Booking booking) {
        // Odanın veritabanında olup olmadığını kontrol et
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Oda belirtilen tarihlerde müsait mi kontrol et
        List<Booking> existingBookings = bookingRepository.findByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(
                room.getId(), booking.getCheckOutDate(), booking.getCheckInDate()
        );

        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        // Yeni rezervasyonu kaydet
        booking.setRoom(room);
        booking.setConfirmed(false); // Başlangıçta rezervasyon onay bekliyor
        return bookingRepository.save(booking);
    }

    /**
     * Tüm rezervasyonları listeleyen metod.
     * @return List<Booking> rezervasyon listesi
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Belirtilen ID'ye sahip rezervasyonu getirir.
     * @param id Rezervasyon ID
     * @return Booking rezervasyon bilgisi
     */
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }



    /**
     * Belirtilen ID'ye sahip bir rezervasyonu günceller.
     * @param id Güncellenecek rezervasyonun ID'si
     * @param updatedBooking Güncellenmiş rezervasyon bilgileri
     * @return Booking güncellenmiş rezervasyon nesnesi
     */
    public Booking updateBooking(Long id, Booking updatedBooking) {
        // Güncellenecek rezervasyonu bul
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Oda belirtilen yeni tarihlerde müsait mi kontrol et
        List<Booking> overlappingBookings = bookingRepository.findByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(
                existingBooking.getRoom().getId(), updatedBooking.getCheckOutDate(), updatedBooking.getCheckInDate()
        );

        // Eğer oda başka bir rezervasyonla çakışıyorsa hata fırlat
        if (!overlappingBookings.isEmpty() && overlappingBookings.stream().anyMatch(b -> !b.getId().equals(id))) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        // Güncellenmiş bilgileri set et
        existingBooking.setCustomerName(updatedBooking.getCustomerName());
        existingBooking.setCustomerEmail(updatedBooking.getCustomerEmail());
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setConfirmed(updatedBooking.isConfirmed());

        return bookingRepository.save(existingBooking);
    }



    /**
     * Belirtilen ID'ye sahip bir rezervasyonu siler.
     * @param id Silinecek rezervasyonun ID'si
     * @return String Rezervasyonun başarıyla silindiğine dair mesaj
     */
    public String deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        bookingRepository.delete(booking);
        return "Booking with ID " + id + " has been successfully deleted.";
    }
}
