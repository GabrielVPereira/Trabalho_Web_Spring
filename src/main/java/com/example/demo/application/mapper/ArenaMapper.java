package com.example.demo.application.mapper;

import com.example.demo.application.dto.ArenaDTO;
import com.example.demo.domain.entity.Arena;
import org.springframework.stereotype.Component;

@Component
public class ArenaMapper {

    public Arena toEntity(ArenaDTO dto) {
        if (dto == null) return null;
        Arena arena = new Arena();
        arena.setId(dto.getId());
        arena.setName(dto.getName());
        arena.setAddress(dto.getAddress());
        return arena;
    }

    public ArenaDTO toDTO(Arena entity) {
        if (entity == null) return null;
        return new ArenaDTO(
            entity.getId(), 
            entity.getName(), 
            entity.getAddress()
        );
    }
}