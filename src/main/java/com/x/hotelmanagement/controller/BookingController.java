package com.x.hotelmanagement.controller;

import com.x.hotelmanagement.entity.Booking;
import com.x.hotelmanagement.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Yeni rezervasyon oluşturan endpoint.
     * @param booking Yeni rezervasyon nesnesi
     * @return ResponseEntity<Booking> oluşturulan rezervasyon
     *
     * (URL: http://localhost:8080/api/bookings/add) + POST
     */
    @PostMapping("/add")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(createdBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
//**************************************************************************************************************
    /**
     * Tüm rezervasyonları getiren endpoint.
     * @return ResponseEntity<List<Booking>> rezervasyon listesi
     *
     * (URL: http://localhost:8080/api/bookings/getAll) + GET
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
//**************************************************************************************************************
    /**
     * Belirtilen ID'ye sahip rezervasyonu getiren endpoint.
     * @param id Rezervasyon ID'si
     * @return ResponseEntity<Booking> rezervasyon bilgisi
     *
     * (URL: http://localhost:8080/api/bookings/get/{id}) + GET
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.getBookingById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
//**************************************************************************************************************
    /**
     * Belirtilen ID'ye sahip rezervasyonu güncelleyen endpoint.
     * @param id Güncellenecek rezervasyonun ID'si
     * @param updatedBooking Güncellenmiş rezervasyon bilgileri
     * @return ResponseEntity<Booking> Güncellenmiş rezervasyon nesnesi
     *
     * (URL: http://localhost:8080/api/bookings/update/{id}) + PUT
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        try {
            Booking updated = bookingService.updateBooking(id, updatedBooking);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
//**************************************************************************************************************
    /**
     * Belirtilen ID'ye sahip rezervasyonu silen endpoint.
     * @param id Silinecek rezervasyonun ID'si
     * @return ResponseEntity<String> Başarı mesajı döner.
     *
     * (URL: http://localhost:8080/api/bookings/delete/{id}) + DELETE
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        try {
            String message = bookingService.deleteBooking(id);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //**************************************************************************************************************
}
