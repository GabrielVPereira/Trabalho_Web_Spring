package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtRepository extends JpaRepository<Court, Long> {
    // filtrar por arena: SELECT * FROM courts WHERE arena_id = :arenaId
    List<Court> findByArena_Id(Long arenaId);

    // busca por esporte (ex.: "FUTSAL")
    List<Court> findBySportIgnoreCase(String sport);

    // busca por nome (auto-complete)
    List<Court> findByNameContainingIgnoreCase(String name);
}
