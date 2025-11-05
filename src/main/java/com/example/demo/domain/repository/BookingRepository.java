package domain.repository;

import domain.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByUser_Id(Long userId, Pageable pageable);

    Page<Booking> findByCourt_Id(Long courtId, Pageable pageable);

    // busca flexível (por campo e/ou intervalo)
    @Query("""
        select b from Booking b
        where (:userId is null  or b.user.id  = :userId)
          and (:courtId is null or b.court.id = :courtId)
          and (:fromTs is null  or b.startAt  >= :fromTs)
          and (:toTs is null    or b.endAt    <= :toTs)
    """)
    List<Booking> search(@Param("userId") Long userId,
                         @Param("courtId") Long courtId,
                         @Param("fromTs") Instant fromTs,
                         @Param("toTs") Instant toTs);

    // detecção de conflito de horário (overlap) para uma quadra
    @Query("""
        select case when count(b) > 0 then true else false end
        from Booking b
        where b.court.id = :courtId
          and b.status <> 'CANCELED'
          and not ( :endAt   <= b.startAt or :startAt >= b.endAt )
    """)
    boolean existsOverlap(@Param("courtId") Long courtId,
                          @Param("startAt") Instant startAt,
                          @Param("endAt") Instant endAt);
}
