package ru.digitalchief.Map.of.attractions.mapper;

import org.mapstruct.*;
import ru.digitalchief.Map.of.attractions.dto.RequestCityDto;
import ru.digitalchief.Map.of.attractions.dto.ResponseCityDto;
import ru.digitalchief.Map.of.attractions.dto.ShortCityDto;
import ru.digitalchief.Map.of.attractions.model.City;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    City toEntity(RequestCityDto requestCityDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City partialUpdate(RequestCityDto requestCityDto, @MappingTarget City city);

    ResponseCityDto toResponseDto(City city);

    ShortCityDto toShortDto(City city);

}