package ru.digitalchief.Map.of.attractions.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalchief.Map.of.attractions.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

    @Override
    Page<City> findAll(Pageable pageable);
}