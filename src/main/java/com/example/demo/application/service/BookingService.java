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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

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

    @Transactional
    public BookingDTO create(BookingCreateDTO dto) {
        User user = users.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("User"));
        Court court = courts.findById(dto.courtId())
                .orElseThrow(() -> new NotFoundException("Court"));

        // regra: impedir conflito de horário (overlap)
        boolean conflict = bookings.existsOverlap(court.getId(), dto.startAt(), dto.endAt());
        if (conflict) {
            throw new BusinessException("Horário indisponível para a quadra.");
        }

        Booking b = new Booking();
        b.setUser(user);
        b.setCourt(court);
        b.setStartAt(dto.startAt());
        b.setEndAt(dto.endAt());
        b.setStatus("PENDING");

        return mapper.toDTO(bookings.save(b));
    }

    @Transactional
    public BookingDTO update(Long id, BookingUpdateDTO dto) {
        Booking b = bookings.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking"));
        b.setStatus(dto.status());
        return mapper.toDTO(bookings.save(b));
    }

    @Transactional
    public void delete(Long id) {
        if (!bookings.existsById(id)) throw new NotFoundException("Booking");
        bookings.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BookingDTO get(Long id) {
        return bookings.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Booking"));
    }

    @Transactional(readOnly = true)
    public Page<BookingDTO> list(Pageable pageable) {
        return bookings.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> search(Long userId, Long courtId, Instant from, Instant to) {
        return bookings.search(userId, courtId, from, to)
                .stream().map(mapper::toDTO).toList();
    }
}
