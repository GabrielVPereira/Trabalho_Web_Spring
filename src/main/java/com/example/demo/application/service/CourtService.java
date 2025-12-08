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
        // 1. Busca a Arena (Pai)
        Arena arena = arenaRepository.findById(dto.getArenaId())
                .orElseThrow(() -> new NotFoundException("Arena não encontrada com ID: " + dto.getArenaId()));

        // 2. Cria a Quadra
        Court court = new Court();
        court.setName(dto.getName());
        court.setSport(dto.getSport());
        court.setArena(arena); // Faz o vínculo

        // 3. Salva e converte para DTO
        return courtMapper.toDTO(courtRepository.save(court));
    }

    @Transactional
    public CourtDTO update(Long id, CourtDTO dto) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quadra não encontrada"));

        // Atualiza campos simples
        court.setName(dto.getName());
        court.setSport(dto.getSport());

        // Se o usuário mandou um ID de arena diferente, atualizamos o vínculo
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
        // Verifica se a arena existe antes (opcional, mas recomendado)
        if (!arenaRepository.existsById(arenaId)) {
            throw new NotFoundException("Arena não encontrada");
        }
        
        return courtRepository.findByArenaId(arenaId).stream()
                .map(courtMapper::toDTO)
                .collect(Collectors.toList());
    }
}