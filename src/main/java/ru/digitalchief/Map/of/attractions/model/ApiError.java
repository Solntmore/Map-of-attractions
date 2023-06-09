package ru.digitalchief.Map.of.attractions.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class ApiError {

   private String message;

   private String reason;

   private HttpStatus status;


}
