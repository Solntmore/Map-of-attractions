package ru.digitalchief.Map.of.attractions.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.Map.of.attractions.client.model.SortDirection;
import ru.digitalchief.Map.of.attractions.client.dto.RequestCityDto;
import ru.digitalchief.Map.of.attractions.client.dto.ResponseCityDto;
import ru.digitalchief.Map.of.attractions.client.model.SortCriteria;
import ru.digitalchief.Map.of.attractions.client.service.CityService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @PostMapping()
    public ResponseEntity<ResponseCityDto> addCity(@RequestBody @Valid RequestCityDto cityDto) {
        log.debug("A Post/cities request was received. Post city");

        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.addCity(cityDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCityDto> updateCity(@RequestBody @Valid RequestCityDto cityDto,
                                                      @PathVariable Long id) {
        log.debug("A Put/cities/{} request was received. Update city", id);

        return ResponseEntity.status(HttpStatus.OK).body(cityService.updateCity(cityDto, id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCityDto>> getCities(
            @RequestParam(required = false, defaultValue = "NAME") SortCriteria sort,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection direction,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size) {
        log.debug("A Get/cities request was received. Get cities");

        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCities(sort, direction, from, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCityDto> getCityById(@PathVariable Long id) {
        log.debug("A Get/cities/{} request was received. Get city by id {}", id, id);

        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCityById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable Long id) {
        log.debug("A Delete/cities/{} request was received. Delete city by id {}", id, id);
        cityService.deleteCityById(id);

        return ResponseEntity.status(204).build();
    }
}
