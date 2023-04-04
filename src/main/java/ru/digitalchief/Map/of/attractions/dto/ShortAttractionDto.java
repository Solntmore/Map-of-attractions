package ru.digitalchief.Map.of.attractions.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.digitalchief.Map.of.attractions.model.Attraction} entity
 */
@Data
public class ShortAttractionDto implements Serializable {

    private final Long id;

    private final String name;
}