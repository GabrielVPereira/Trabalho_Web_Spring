package com.example.demo.controller;

import com.example.demo.application.dto.ArenaDTO;
import com.example.demo.application.service.ArenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/arenas")
@RequiredArgsConstructor
public class ArenaController {

    private final ArenaService arenaService;

    @GetMapping
    public ResponseEntity<List<ArenaDTO>> findAll() {
        return ResponseEntity.ok(arenaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArenaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(arenaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ArenaDTO> create(@RequestBody ArenaDTO dto) {
        ArenaDTO newArena = arenaService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newArena.getId()).toUri();
        return ResponseEntity.created(uri).body(newArena);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArenaDTO> update(@PathVariable Long id, @RequestBody ArenaDTO dto) {
        return ResponseEntity.ok(arenaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        arenaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}