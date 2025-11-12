package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Arena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArenaRepository extends JpaRepository<Arena, Long> {
    List<Arena> findByNameContainingIgnoreCase(String name);
}
