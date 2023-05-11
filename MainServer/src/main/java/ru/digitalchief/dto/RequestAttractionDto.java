package ru.digitalchief.dto;

import lombok.Builder;
import lombok.Data;
import ru.digitalchief.model.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Builder
@Data
public class RequestAttractionDto implements Serializable {

    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final Long cityId;

    @NotNull
    private final Status status;

    @NotBlank
    private final String website;
}