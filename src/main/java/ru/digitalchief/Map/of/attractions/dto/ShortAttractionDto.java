package ru.digitalchief.Map.of.attractions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.digitalchief.Map.of.attractions.model.Attraction} entity
 */
@Data
@Builder
@Jacksonized
public class ShortAttractionDto implements Serializable {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;
}