package ru.digitalchief.Map.of.attractions.dto;

import lombok.Builder;
import lombok.Data;
import ru.digitalchief.Map.of.attractions.model.City;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * A DTO for the {@link City} entity
 */
@Builder
@Data
public class RequestCityDto implements Serializable {

    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final Long population;

    @NotNull
    @Positive
    private final int area;

    @NotBlank
    private final String website;
}