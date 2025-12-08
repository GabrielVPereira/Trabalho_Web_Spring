package com.example.demo.application.service;

import com.example.demo.application.dto.BookingCreateDTO;
import com.example.demo.application.dto.BookingDTO;
import com.example.demo.application.dto.BookingUpdateDTO;
import com.example.demo.application.mapper.BookingMapper;
import com.example.demo.domain.entity.Booking;
import com.example.demo.domain.entity.Court;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.BookingRepository;
import com.example.demo.domain.repository.CourtRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.application.exception.NotFoundException;
import com.example.demo.application.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookings;
    private final UserRepository users;
    private final CourtRepository courts;
    private final BookingMapper mapper;

    public BookingService(BookingRepository bookings,
                          UserRepository users,
                          CourtRepository courts,
                          BookingMapper mapper) {
        this.bookings = bookings;
        this.users = users;
        this.courts = courts;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> findAll() {
        return bookings.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookingDTO findById(Long id) {
        return bookings.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Booking not found with id: " + id));
    }

    @Transactional
    public BookingDTO create(BookingCreateDTO dto) {
        // CORREÇÃO: Usando .getUserId() ao invés de .userId()
        User user = users.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        
        // CORREÇÃO: Usando .getCourtId() ao invés de .courtId()
        Court court = courts.findById(dto.getCourtId())
                .orElseThrow(() -> new NotFoundException("Court not found"));

        // CORREÇÃO: Usando .getStartAt() e .getEndAt()
        boolean conflict = bookings.existsOverlap(court.getId(), dto.getStartAt(), dto.getEndAt());
        if (conflict) {
            throw new BusinessException("Horário indisponível para esta quadra.");
        }

        Booking b = new Booking();
        b.setUser(user);
        b.setCourt(court);
        b.setStartAt(dto.getStartAt()); // CORREÇÃO
        b.setEndAt(dto.getEndAt());     // CORREÇÃO
        b.setStatus("PENDING");

        return mapper.toDTO(bookings.save(b));
    }

    @Transactional
    public BookingDTO update(Long id, BookingUpdateDTO dto) {
        Booking b = bookings.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        
        // CORREÇÃO: Usando .getStatus() ao invés de .status()
        if (dto.getStatus() != null) {
            b.setStatus(dto.getStatus());
        }
        
        return mapper.toDTO(bookings.save(b));
    }

    @Transactional
    public void delete(Long id) {
        if (!bookings.existsById(id)) {
            throw new NotFoundException("Booking not found to delete");
        }
        bookings.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> search(Long userId, Long courtId, Instant from, Instant to) {
        return bookings.search(userId, courtId, from, to)
                .stream().map(mapper::toDTO).toList();
    }
}