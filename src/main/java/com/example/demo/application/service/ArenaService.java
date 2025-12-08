package com.example.demo.application.service;

import com.example.demo.application.dto.ArenaDTO;
import com.example.demo.application.mapper.ArenaMapper;
import com.example.demo.domain.entity.Arena;
import com.example.demo.domain.repository.ArenaRepository;
import com.example.demo.application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArenaService {

    private final ArenaRepository arenaRepository;
    private final ArenaMapper arenaMapper;

    @Transactional(readOnly = true)
    public List<ArenaDTO> findAll() {
        return arenaRepository.findAll().stream()
                .map(arenaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArenaDTO findById(Long id) {
        return arenaRepository.findById(id)
                .map(arenaMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Arena não encontrada"));
    }

    @Transactional
    public ArenaDTO create(ArenaDTO dto) {
        Arena entity = arenaMapper.toEntity(dto);
        // Garante que o ID é nulo para criar novo
        entity.setId(null); 
        return arenaMapper.toDTO(arenaRepository.save(entity));
    }

    @Transactional
    public ArenaDTO update(Long id, ArenaDTO dto) {
        Arena entity = arenaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Arena não encontrada"));
        
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        
        return arenaMapper.toDTO(arenaRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!arenaRepository.existsById(id)) {
            throw new NotFoundException("Arena não encontrada");
        }
        arenaRepository.deleteById(id);
    }
}