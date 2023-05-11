package ru.digitalchief.mapper;

import org.mapstruct.*;
import ru.digitalchief.dto.RequestCityDto;
import ru.digitalchief.dto.ResponseCityDto;
import ru.digitalchief.dto.ShortCityDto;
import ru.digitalchief.model.City;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    City toEntity(RequestCityDto requestCityDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City partialUpdate(RequestCityDto requestCityDto, @MappingTarget City city);

    ResponseCityDto toResponseDto(City city);

    ShortCityDto toShortDto(City city);

}