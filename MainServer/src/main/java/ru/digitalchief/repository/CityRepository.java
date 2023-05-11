package ru.digitalchief.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalchief.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

    @Override
    Page<City> findAll(Pageable pageable);
}