package ru.digitalchief.Map.of.attractions.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.digitalchief.Map.of.attractions.model.City} entity
 */
@Data
public class ShortCityDto implements Serializable {

    private final Long id;

    private final String name;
}