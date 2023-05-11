package ru.digitalchief.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.digitalchief.model.Status;

import java.io.Serializable;

@Data
@Builder
@Jacksonized
public class ResponseAttractionDto implements Serializable {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("city")
    private final ShortCityDto city;

    @JsonProperty("status")
    private final Status status;

    @JsonProperty("website")
    private final String website;
}