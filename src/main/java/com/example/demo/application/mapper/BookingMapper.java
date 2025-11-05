package com.example.demo.application.mapper;
import com.example.demo.application.dto.BookingDTO;
import com.example.demo.domain.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingDTO toDTO(Booking b) {
        if (b == null) return null;
        return new BookingDTO(
                b.getId(),
                b.getUser().getId(),
                b.getCourt().getId(),
                b.getStartAt(),
                b.getEndAt(),
                b.getStatus()
        );
    }
}
