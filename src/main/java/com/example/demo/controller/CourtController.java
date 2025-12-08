package com.example.demo.controller;

import com.example.demo.application.dto.CourtDTO;
import com.example.demo.application.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public ResponseEntity<List<CourtDTO>> findAll() {
        return ResponseEntity.ok(courtService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.findById(id));
    }

    @GetMapping("/arena/{arenaId}")
    public ResponseEntity<List<CourtDTO>> findByArena(@PathVariable Long arenaId) {
        return ResponseEntity.ok(courtService.findByArenaId(arenaId));
    }

    @PostMapping
    public ResponseEntity<CourtDTO> create(@RequestBody CourtDTO dto) {
        CourtDTO newCourt = courtService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newCourt.getId()).toUri();
        return ResponseEntity.created(uri).body(newCourt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtDTO> update(@PathVariable Long id, @RequestBody CourtDTO dto) {
        return ResponseEntity.ok(courtService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}