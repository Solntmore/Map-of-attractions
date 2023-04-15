package ru.digitalchief.Map.of.attractions.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.digitalchief.Map.of.attractions.dto.RequestCityDto;
import ru.digitalchief.Map.of.attractions.dto.ResponseCityDto;
import ru.digitalchief.Map.of.attractions.exception.CityNotFoundException;
import ru.digitalchief.Map.of.attractions.mapper.CityMapper;
import ru.digitalchief.Map.of.attractions.model.City;
import ru.digitalchief.Map.of.attractions.model.SortCriteria;
import ru.digitalchief.Map.of.attractions.model.SortDirection;
import ru.digitalchief.Map.of.attractions.repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Caching(evict = {
            @CacheEvict(cacheNames = {"citiesListCache"}, allEntries = true)
            })
    public ResponseCityDto addCity(RequestCityDto cityDto) {
        City savedCity = cityRepository.save(cityMapper.toEntity(cityDto));

        return cityMapper.toResponseDto(savedCity);
    }

    @Caching(put = {
            @CachePut(cacheNames = {"cityByIdCache"}, key = "#id"),
    },
            evict = {
            @CacheEvict(cacheNames = {"citiesListCache"}, allEntries = true),
            @CacheEvict(cacheNames = {"attractionByIdCache"}, allEntries = true),
            @CacheEvict(cacheNames = {"attractionsListCache"}, allEntries = true)
            })
    public ResponseCityDto updateCity(RequestCityDto cityDto, Long id) {
        City city = cityRepository.findById(id).orElseThrow(() ->
                new CityNotFoundException("City with id " + id + " is not found"));
        City updateCity = cityMapper.partialUpdate(cityDto, city);

        return cityMapper.toResponseDto(cityRepository.save(updateCity));
    }

    @Cacheable("citiesListCache")
    public List<ResponseCityDto> getCities(SortCriteria sort, SortDirection direction, int from, int size) {
        if (direction.equals(SortDirection.ASC)) {

            return cityRepository.findAll(PageRequest.of(from, size, Sort.by(sort.name().toLowerCase())))
                    .stream()
                    .map(cityMapper::toResponseDto)
                    .collect(Collectors.toList());
        } else {

            return cityRepository.findAll(PageRequest.of(from, size, Sort.by(sort.name().toLowerCase()).descending()))
                    .stream()
                    .map(cityMapper::toResponseDto)
                    .collect(Collectors.toList());
        }
    }

    @Cacheable(cacheNames = {"cityByIdCache"}, key = "#id")
    public ResponseCityDto getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() ->
                new CityNotFoundException("City with id " + id + " is not found"));

        return cityMapper.toResponseDto(city);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = {"cityByIdCache"}, key = "#id"),
            @CacheEvict(cacheNames = {"citiesListCache"}, allEntries = true),
            @CacheEvict(cacheNames = {"attractionByIdCache"}, allEntries = true),
            @CacheEvict(cacheNames = {"attractionsListCache"}, allEntries = true)
    })
    public void deleteCityById(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new CityNotFoundException("City with id: " + id + " is not found");
        }

        cityRepository.deleteById(id);
    }
}
