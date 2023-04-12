package ru.digitalchief.Map.of.attractions.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.digitalchief.Map.of.attractions.dto.RequestAttractionDto;
import ru.digitalchief.Map.of.attractions.dto.ResponseAttractionDto;
import ru.digitalchief.Map.of.attractions.exception.AttractionNotFoundException;
import ru.digitalchief.Map.of.attractions.exception.CityNotFoundException;
import ru.digitalchief.Map.of.attractions.mapper.AttractionMapper;
import ru.digitalchief.Map.of.attractions.model.Attraction;
import ru.digitalchief.Map.of.attractions.model.City;
import ru.digitalchief.Map.of.attractions.model.Status;
import ru.digitalchief.Map.of.attractions.repository.AttractionRepository;
import ru.digitalchief.Map.of.attractions.repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final CityRepository cityRepository;

    private final AttractionRepository attractionRepository;

    private final AttractionMapper attractionMapper;

    @CacheEvict(value = {"citiesCache"}, key = "#attractionDto.cityId")
    public ResponseAttractionDto addAttraction(RequestAttractionDto attractionDto) {
        City city = cityRepository.findById(attractionDto.getCityId()).orElseThrow(() ->
                new CityNotFoundException("City for this attraction is not found"));

        Attraction savedAttraction = attractionRepository.save(
                attractionMapper.toEntity(attractionDto));
        savedAttraction.setCity(city);

        return attractionMapper.toFullDto(savedAttraction);

    }

    @Caching(put = {
            @CachePut(cacheNames = {"attractionsCache"}, key = "#id")},
            evict = {
            @CacheEvict(value = {"citiesCache"}, key = "#attractionDto.cityId")})
    public ResponseAttractionDto updateAttraction(RequestAttractionDto attractionDto, Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() ->
                new AttractionNotFoundException("Attraction with id: " + id + " is not found"));

        if (!isCityExist(attractionDto.getCityId())) {
            throw new CityNotFoundException("City for this attraction is not found");
        }

        Attraction updateAttraction = attractionMapper.partialUpdate(attractionDto, attraction);

        return attractionMapper.toFullDto(attractionRepository.save(updateAttraction));
    }

    public List<ResponseAttractionDto> getAttractions(Status status, PageRequest of) {

        return attractionRepository.findAllByStatus(status, of)
                .stream()
                .map(attractionMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = {"attractionsCache"}, key = "#id")
    public ResponseAttractionDto getAttractionById(Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() ->
                new AttractionNotFoundException("Attraction with id: " + id + " is not found"));

        return attractionMapper.toFullDto(attraction);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = {"attractionsCache"}, key = "#id"),
            @CacheEvict(value = "citiesCache", allEntries = true)
    })
    public void deleteAttractionById(Long id) {
        if (!attractionRepository.existsById(id)) {
            throw new AttractionNotFoundException("Attraction with id: " + id + " is not found");
        }

        attractionRepository.deleteById(id);
    }

    private boolean isCityExist(Long id) {
        return cityRepository.existsById(id);
    }
}
