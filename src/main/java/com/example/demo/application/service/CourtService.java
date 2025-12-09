package com.example.demo.application.service;

import com.example.demo.application.dto.CourtDTO;
import com.example.demo.application.mapper.CourtMapper;
import com.example.demo.domain.entity.Arena;
import com.example.demo.domain.entity.Court;
import com.example.demo.domain.repository.ArenaRepository;
import com.example.demo.domain.repository.CourtRepository;
import com.example.demo.application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;
    private final ArenaRepository arenaRepository;
    private final CourtMapper courtMapper;

    @Transactional(readOnly = true)
    public List<CourtDTO> findAll() {
        return courtRepository.findAll().stream()
                .map(courtMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourtDTO findById(Long id) {
        return courtRepository.findById(id)
                .map(courtMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Quadra não encontrada"));
    }

    @Transactional
    public CourtDTO create(CourtDTO dto) {
        Arena arena = arenaRepository.findById(dto.getArenaId())
                .orElseThrow(() -> new NotFoundException("Arena não encontrada com ID: " + dto.getArenaId()));

        
        Court court = new Court();
        court.setName(dto.getName());
        court.setSport(dto.getSport());
        court.setArena(arena); 

        
        return courtMapper.toDTO(courtRepository.save(court));
    }

    @Transactional
    public CourtDTO update(Long id, CourtDTO dto) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quadra não encontrada"));

        
        court.setName(dto.getName());
        court.setSport(dto.getSport());

       
        if (dto.getArenaId() != null && !dto.getArenaId().equals(court.getArena().getId())) {
             Arena novaArena = arenaRepository.findById(dto.getArenaId())
                .orElseThrow(() -> new NotFoundException("Nova Arena não encontrada"));
             court.setArena(novaArena);
        }

        return courtMapper.toDTO(courtRepository.save(court));
    }

    @Transactional
    public void delete(Long id) {
        if (!courtRepository.existsById(id)) {
            throw new NotFoundException("Quadra não encontrada");
        }
        courtRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CourtDTO> findByArenaId(Long arenaId) {
        if (!arenaRepository.existsById(arenaId)) {
            throw new NotFoundException("Arena não encontrada");
        }
        
        return courtRepository.findByArenaId(arenaId).stream()
                .map(courtMapper::toDTO)
                .collect(Collectors.toList());
    }
}
