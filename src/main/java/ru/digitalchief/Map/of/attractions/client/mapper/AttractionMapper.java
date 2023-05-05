package ru.digitalchief.Map.of.attractions.client.mapper;

import org.mapstruct.*;
import ru.digitalchief.Map.of.attractions.client.dto.RequestAttractionDto;
import ru.digitalchief.Map.of.attractions.client.dto.ShortAttractionDto;
import ru.digitalchief.Map.of.attractions.client.dto.ResponseAttractionDto;
import ru.digitalchief.Map.of.attractions.client.model.Attraction;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CityMapper.class})
public interface AttractionMapper {

    @Mapping(source = "cityId", target = "city.id")
    Attraction toEntity(RequestAttractionDto requestAttractionDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "cityId", target = "city.id")
    Attraction partialUpdate(RequestAttractionDto requestAttractionDto, @MappingTarget Attraction attraction);

    ResponseAttractionDto toFullDto(Attraction attraction);

    ShortAttractionDto toShortDto(Attraction attraction);
}