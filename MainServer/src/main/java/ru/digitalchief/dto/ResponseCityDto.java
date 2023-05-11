package ru.digitalchief.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.Set;

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