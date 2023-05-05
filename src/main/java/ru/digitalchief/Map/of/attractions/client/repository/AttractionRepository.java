package ru.digitalchief.Map.of.attractions.client.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalchief.Map.of.attractions.client.model.Attraction;
import ru.digitalchief.Map.of.attractions.client.model.Status;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Page<Attraction> findAllByStatus(Status status, Pageable pageable);
}