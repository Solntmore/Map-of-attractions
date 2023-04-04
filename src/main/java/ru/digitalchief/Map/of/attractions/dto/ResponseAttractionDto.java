package ru.digitalchief.Map.of.attractions.dto;

import lombok.Data;
import ru.digitalchief.Map.of.attractions.model.Status;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.digitalchief.Map.of.attractions.model.Attraction} entity
 */
@Data
public class ResponseAttractionDto implements Serializable {
    private final Long id;
    private final String name;
    private final ShortCityDto city;
    private final Status status;
    private final String website;
}