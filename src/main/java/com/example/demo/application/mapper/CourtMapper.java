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
            // Verifica se tem arena para evitar erro, embora seja obrigatório no banco
            entity.getArena() != null ? entity.getArena().getId() : null
        );
    }
    
    // Obs: Não faremos toEntity aqui porque precisamos buscar a Arena no banco
    // Faremos a montagem do objeto 'Court' diretamente no Service, igual fizemos no Booking.
}