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

    // Consulta de Todos Registros
    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {
        return ResponseEntity.ok(bookingService.findAll()); // Assumindo método findAll no service
    }

    // Consulta de Registro por Chave Primária
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    // Criação de Novo Registro
    @PostMapping
    public ResponseEntity<BookingDTO> create(@RequestBody BookingCreateDTO dto) {
        BookingDTO newBooking = bookingService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBooking.getId()).toUri(); // Ajuste getId() conforme seu DTO
        return ResponseEntity.created(uri).body(newBooking);
    }

    // Atualização de Registro
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable Long id, @RequestBody BookingUpdateDTO dto) {
        return ResponseEntity.ok(bookingService.update(id, dto));
    }

    // Exclusão de Registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<BookingDTO>> search(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long courtId,
            @RequestParam(required = false) String from, // Data ISO (ex: 2025-01-01T00:00:00Z)
            @RequestParam(required = false) String to
    ) {
        // Converte Strings para Instant se não forem nulas
        java.time.Instant fromInstant = (from != null) ? java.time.Instant.parse(from) : null;
        java.time.Instant toInstant = (to != null) ? java.time.Instant.parse(to) : null;

        return ResponseEntity.ok(bookingService.search(userId, courtId, fromInstant, toInstant));
    }
    
}