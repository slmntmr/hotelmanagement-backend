package com.x.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room; // Rezervasyonun bağlı olduğu oda

    private String customerName; // Rezervasyonu yapan müşteri adı
    private String customerEmail; // Müşteri e-posta adresi

    private LocalDate checkInDate; // Giriş tarihi
    private LocalDate checkOutDate; // Çıkış tarihi

    private boolean isConfirmed; // Rezervasyonun onaylanıp onaylanmadığı
}
