package ru.digitalchief.Map.of.attractions.client.mapper;

import org.mapstruct.*;
import ru.digitalchief.Map.of.attractions.client.dto.RequestCityDto;
import ru.digitalchief.Map.of.attractions.client.dto.ResponseCityDto;
import ru.digitalchief.Map.of.attractions.client.dto.ShortCityDto;
import ru.digitalchief.Map.of.attractions.client.model.City;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    City toEntity(RequestCityDto requestCityDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City partialUpdate(RequestCityDto requestCityDto, @MappingTarget City city);

    ResponseCityDto toResponseDto(City city);

    ShortCityDto toShortDto(City city);

}