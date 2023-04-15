package ru.digitalchief.Map.of.attractions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.digitalchief.Map.of.attractions.model.City;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link City} entity
 */
@Data
@Builder
@Jacksonized
public class ResponseCityDto implements Serializable {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("population")
    private final Long population;

    @JsonProperty("area")
    private final int area;

    @JsonProperty("website")
    private final String website;

    @JsonProperty("attractions")
    private Set<ShortAttractionDto> attractions;

}