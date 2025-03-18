package com.x.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // Single, Double, Suite vb.

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean available; // Oda dolu mu boş mu?
}
