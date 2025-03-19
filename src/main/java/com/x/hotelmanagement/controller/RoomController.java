package com.x.hotelmanagement.controller;

import com.x.hotelmanagement.entity.Room;
import com.x.hotelmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir REST API Controller olduğunu belirtir.
@RequestMapping("/api/rooms") // Tüm endpointler "/api/rooms" altında çalışacak.
@RequiredArgsConstructor // RoomService bağımlılığını constructor ile enjekte eder.
public class RoomController {

    private final RoomService roomService; // Servis katmanını enjekte ediyoruz.

    /**
     * Tüm odaları listeleyen endpoint.
     * @return ResponseEntity<List<Room>> Tüm odaların listesi JSON formatında döner.
     *
     * (URL: http://localhost:8080/api/rooms/getAll) + GET
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms()); // 200 OK ile odaları döndür.
    }
//***************************************************************************************
    /**
     * Belirtilen ID'ye sahip bir odayı getiren endpoint.
     * @param id Oda ID'si
     * @return ResponseEntity<Room> Oda nesnesi JSON formatında döner.
     *
     * (URL: http://localhost:8080/api/rooms/get/{id}) + GET
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id)); // 200 OK ile belirtilen odayı döndür.
    }
//***************************************************************************************
    /**
     * Yeni bir oda ekleyen endpoint.
     * @param room Request Body içinde gelen oda nesnesi
     * @return ResponseEntity<Room> Oluşturulan oda nesnesi
     *
     * (URL: http://localhost:8080/api/rooms/add) + POST
     */
    @PostMapping("/add")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.createRoom(room)); // 200 OK ile yeni odayı döndür.
    }
//***************************************************************************************
    /**
     * Belirtilen ID'ye sahip bir odayı güncelleyen endpoint.
     * @param id Güncellenecek oda ID'si
     * @param updatedRoom Güncellenmiş oda bilgileri
     * @return ResponseEntity<Room> Güncellenmiş oda nesnesi
     *
     * (URL: http://localhost:8080/api/rooms/update/{id}) + PUT
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room updatedRoom) {
        return ResponseEntity.ok(roomService.updateRoom(id, updatedRoom)); // 200 OK ile güncellenmiş odayı döndür.
    }
//***************************************************************************************
    /**
     * Belirtilen ID'ye sahip bir odayı silen endpoint.
     * @param id Silinecek oda ID'si
     * @return ResponseEntity<String> Başarı mesajı döner.
     *
     * (URL: http://localhost:8080/api/rooms/delete/{id}) + DELETE
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        String message = roomService.deleteRoom(id); // Odayı sil ve mesaj al.
        return ResponseEntity.ok(message); // 200 OK ile mesajı döndür.
    }

    //***************************************************************************************
}
