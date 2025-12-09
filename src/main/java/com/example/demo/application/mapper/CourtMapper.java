package com.example.demo.application.mapper;

import com.example.demo.application.dto.CourtDTO;
import com.example.demo.domain.entity.Court;
import org.springframework.stereotype.Component;

@Component
public class CourtMapper {

    
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
