package ru.digitalchief.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.dto.RequestAttractionDto;
import ru.digitalchief.dto.ResponseAttractionDto;
import ru.digitalchief.model.Status;
import ru.digitalchief.service.AttractionService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping()
    public ResponseEntity<ResponseAttractionDto> addAttraction(@RequestBody @Valid RequestAttractionDto attractionDto) {
        log.debug("A Post/attractions request was received. Post attraction");

        return ResponseEntity.status(HttpStatus.CREATED).body(attractionService.addAttraction(attractionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseAttractionDto> updateAttraction(@RequestBody @Valid RequestAttractionDto attractionDto,
                                                                  @PathVariable Long id) {
        log.debug("A Put/attractions/{} request was received. Update attraction", id);

        return ResponseEntity.status(HttpStatus.OK).body(attractionService.updateAttraction(attractionDto, id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseAttractionDto>> getAttractions(
                                             @RequestParam(required = false, defaultValue = "AVAILABLE") Status status,
                                             @RequestParam(required = false, defaultValue = "0") int from,
                                             @RequestParam(required = false, defaultValue = "10") int size) {
        log.debug("A Get/attractions request was received. Get attractions");

        return ResponseEntity.status(HttpStatus.OK).body(attractionService.getAttractions(status,
                PageRequest.of(from, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAttractionDto> getAttractionById(@PathVariable Long id) {
        log.debug("A Get/attractions/{} request was received. Get attraction by id {}", id, id);

        return ResponseEntity.status(HttpStatus.OK).body(attractionService.getAttractionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttractionById(@PathVariable Long id) {
        log.debug("A Delete/attractions/{} request was received. Delete attraction by id {}", id, id);
        attractionService.deleteAttractionById(id);

        return ResponseEntity.status(204).build();
    }


}
