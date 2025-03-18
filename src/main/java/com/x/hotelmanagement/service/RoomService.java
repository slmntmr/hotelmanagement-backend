package com.x.hotelmanagement.service;


import com.x.hotelmanagement.entity.Room;
import com.x.hotelmanagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Servis katmanı olduğunu belirtir.
@RequiredArgsConstructor // Lombok ile constructor enjeksiyonu sağlar.
public class RoomService {

    private final RoomRepository roomRepository; // RoomRepository'yi enjekte ediyoruz.

    /**
     * Tüm odaları listeleyen metod.
     * @return List<Room> tüm odaların listesi
     */
    public List<Room> getAllRooms() {
        return roomRepository.findAll(); // Veritabanındaki tüm odaları getir.
    }

    /**
     * Belirtilen ID'ye sahip bir odayı getiren metod.
     * @param id Oda ID'si
     * @return Room bulunursa, oda nesnesi döner.
     */
    public Room getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id); // ID'ye göre odayı ara.
        return room.orElseThrow(() -> new RuntimeException("Oda bulunamadı!")); // Oda yoksa hata fırlat.
    }

    /**
     * Yeni bir oda ekleyen metod.
     * @param room Kaydedilecek oda nesnesi
     * @return Room kaydedilen oda nesnesi
     */
    public Room createRoom(Room room) {
        return roomRepository.save(room); // Yeni odayı kaydet.
    }

    /**
     * Mevcut bir odayı güncelleyen metod.
     * @param id Güncellenecek oda ID'si
     * @param updatedRoom Güncellenmiş oda bilgileri
     * @return Room güncellenmiş oda nesnesi
     */
    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = getRoomById(id); // Güncellenecek odayı bul.

        // Mevcut odanın bilgilerini güncelle
        existingRoom.setName(updatedRoom.getName());
        existingRoom.setType(updatedRoom.getType());
        existingRoom.setCapacity(updatedRoom.getCapacity());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setAvailable(updatedRoom.isAvailable());

        return roomRepository.save(existingRoom); // Güncellenmiş odayı kaydet.
    }

    /**
     * Bir odayı silen metod.
     * @param id Silinecek oda ID'si
     */
    public void deleteRoom(Long id) {
        Room room = getRoomById(id); // Silinecek odayı bul.
        roomRepository.delete(room); // Odayı veritabanından sil.
    }
}
