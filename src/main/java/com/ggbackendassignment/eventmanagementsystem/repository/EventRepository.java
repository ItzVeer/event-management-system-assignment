package com.ggbackendassignment.eventmanagementsystem.repository;

import com.ggbackendassignment.eventmanagementsystem.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e where e.eventDate between :startDate and :endDate ORDER BY CONCAT(e.eventDate, ' ', e.eventTime) ASC")
    List<Event> findByEventDateBetweenOrderByEventDate(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
