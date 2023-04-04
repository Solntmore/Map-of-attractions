package ru.digitalchief.Map.of.attractions.dto;

import lombok.Data;
import ru.digitalchief.Map.of.attractions.model.City;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link City} entity
 */
@Data
public class ResponseCityDto implements Serializable {
    private final Long id;
    private final String name;
    private final Long population;
    private final int area;
    private final String website;
    private Set<ShortAttractionDto> attractions;
}