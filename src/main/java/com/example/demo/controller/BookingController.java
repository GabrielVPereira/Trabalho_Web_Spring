package com.example.demo.controller;

import com.example.demo.application.dto.BookingCreateDTO;
import com.example.demo.application.dto.BookingDTO;
import com.example.demo.application.dto.BookingUpdateDTO;
import com.example.demo.application.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    
    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {
        return ResponseEntity.ok(bookingService.findAll()); 
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

   
    @PostMapping
    public ResponseEntity<BookingDTO> create(@RequestBody BookingCreateDTO dto) {
        BookingDTO newBooking = bookingService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBooking.getId()).toUri(); 
        return ResponseEntity.created(uri).body(newBooking);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable Long id, @RequestBody BookingUpdateDTO dto) {
        return ResponseEntity.ok(bookingService.update(id, dto));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<BookingDTO>> search(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long courtId,
            @RequestParam(required = false) String from, 
            @RequestParam(required = false) String to
    ) {
        
        java.time.Instant fromInstant = (from != null) ? java.time.Instant.parse(from) : null;
        java.time.Instant toInstant = (to != null) ? java.time.Instant.parse(to) : null;

        return ResponseEntity.ok(bookingService.search(userId, courtId, fromInstant, toInstant));
    }
    
}
