package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Query para verificar sobreposição de horários
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
           "FROM Booking b " +
           "WHERE b.court.id = :courtId " +
           "AND b.status <> 'CANCELED' " +
           "AND ((b.startAt < :endAt) AND (b.endAt > :startAt))")
    boolean existsOverlap(@Param("courtId") Long courtId, 
                          @Param("startAt") Instant startAt, 
                          @Param("endAt") Instant endAt);

    // Query para busca avançada (usada no método search do Service)
   @Query("SELECT b FROM Booking b WHERE " +
           "b.user.id = COALESCE(:userId, b.user.id) AND " +
           "b.court.id = COALESCE(:courtId, b.court.id) AND " +
           "b.startAt >= COALESCE(:from, b.startAt) AND " +
           "b.endAt <= COALESCE(:to, b.endAt)")
    List<Booking> search(@Param("userId") Long userId,
                         @Param("courtId") Long courtId,
                         @Param("from") Instant from,
                         @Param("to") Instant to);
}