package com.x.hotelmanagement.controller;

import com.x.hotelmanagement.entity.Booking;
import com.x.hotelmanagement.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }
//*****************************************************************************************************
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

    /**
     * Belirtilen ID'ye sahip rezervasyonu getiren endpoint.
     * @param id Rezervasyon ID'si
     * @return ResponseEntity<Booking> rezervasyon bilgisi
     *
     * (URL: http://localhost:8080/api/bookings/get/{id}) + GET
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    /**
     * Belirtilen ID'ye sahip rezervasyonu güncelleyen endpoint.
     * @param id Güncellenecek rezervasyonun ID'si
     * @param updatedBooking Güncellenmiş rezervasyon bilgileri
     * @return ResponseEntity<Booking> Güncellenmiş rezervasyon nesnesi
     *
     * (URL: http://localhost:8080/api/bookings/update/{id}) + PUT
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, updatedBooking));
    }

    /**
     * Belirtilen ID'ye sahip rezervasyonu silen endpoint.
     * @param id Silinecek rezervasyonun ID'si
     * @return ResponseEntity<String> Başarı mesajı döner.
     *
     * (URL: http://localhost:8080/api/bookings/delete/{id}) + DELETE
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        String message = bookingService.deleteBooking(id);
        return ResponseEntity.ok(message);
    }



}
