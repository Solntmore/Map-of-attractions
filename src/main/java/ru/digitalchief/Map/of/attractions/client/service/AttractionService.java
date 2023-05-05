package ru.digitalchief.Map.of.attractions.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.digitalchief.Map.of.attractions.client.dto.RequestAttractionDto;
import ru.digitalchief.Map.of.attractions.client.exception.AttractionNotFoundException;
import ru.digitalchief.Map.of.attractions.client.exception.CityNotFoundException;
import ru.digitalchief.Map.of.attractions.client.dto.ResponseAttractionDto;
import ru.digitalchief.Map.of.attractions.client.mapper.AttractionMapper;
import ru.digitalchief.Map.of.attractions.client.model.Attraction;
import ru.digitalchief.Map.of.attractions.client.model.City;
import ru.digitalchief.Map.of.attractions.client.model.Status;
import ru.digitalchief.Map.of.attractions.client.repository.AttractionRepository;
import ru.digitalchief.Map.of.attractions.client.repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final CityRepository cityRepository;

    private final AttractionRepository attractionRepository;

    private final AttractionMapper attractionMapper;

    @Caching(evict = {
            @CacheEvict(value = {"cityByIdCache"}, key = "#attractionDto.cityId"),
            @CacheEvict(value = {"citiesListCache"}, allEntries = true),
            @CacheEvict(value = {"attractionsListCache"}, allEntries = true)
    })
    public ResponseAttractionDto addAttraction(RequestAttractionDto attractionDto) {
        City city = cityRepository.findById(attractionDto.getCityId()).orElseThrow(() ->
                new CityNotFoundException("City for this attraction is not found"));

        Attraction savedAttraction = attractionRepository.save(
                attractionMapper.toEntity(attractionDto));
        savedAttraction.setCity(city);

        return attractionMapper.toFullDto(savedAttraction);

    }

    @Caching(put = {
            @CachePut(cacheNames = {"attractionByIdCache"}, key = "#id")},
            evict = {
            @CacheEvict(cacheNames = {"attractionsListCache"}, allEntries = true),
            @CacheEvict(value = {"cityByIdCache"}, key = "#attractionDto.cityId"),
            @CacheEvict(value = {"citiesListCache"}, allEntries = true)})
    public ResponseAttractionDto updateAttraction(RequestAttractionDto attractionDto, Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() ->
                new AttractionNotFoundException("Attraction with id: " + id + " is not found"));

        if (!isCityExist(attractionDto.getCityId())) {
            throw new CityNotFoundException("City for this attraction is not found");
        }

        Attraction updateAttraction = attractionMapper.partialUpdate(attractionDto, attraction);

        return attractionMapper.toFullDto(attractionRepository.save(updateAttraction));
    }

    @Cacheable("attractionsListCache")
    public List<ResponseAttractionDto> getAttractions(Status status, PageRequest of) {

        return attractionRepository.findAllByStatus(status, of)
                .stream()
                .map(attractionMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = {"attractionByIdCache"}, key = "#id")
    public ResponseAttractionDto getAttractionById(Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() ->
                new AttractionNotFoundException("Attraction with id: " + id + " is not found"));

        return attractionMapper.toFullDto(attraction);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = {"attractionByIdCache"}, key = "#id"),
            @CacheEvict(cacheNames = {"attractionsListCache"}, allEntries = true),
            @CacheEvict(value = "cityByIdCache", allEntries = true),
            @CacheEvict(value = {"citiesListCache"}, allEntries = true)
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
