package com.example.demo.application.mapper;

import com.example.demo.application.dto.CourtDTO;
import com.example.demo.domain.entity.Court;
import org.springframework.stereotype.Component;

@Component
public class CourtMapper {

    // Converter Entidade -> DTO (Para devolver na API)
    public CourtDTO toDTO(Court entity) {
        if (entity == null) return null;
        
        return new CourtDTO(
            entity.getId(),
            entity.getName(),
            entity.getSport(),

            entity.getArena() != null ? entity.getArena().getId() : null
        );
    }
    

}
