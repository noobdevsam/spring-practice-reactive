package com.example.springpracticereactive.mappers;

import com.example.springpracticereactive.domain.Beer;
import com.example.springpracticereactive.model.BeerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Beer and BeerDTO objects.
 * Utilizes MapStruct for automatic mapping implementation.
 */
@Mapper(componentModel = "spring")
public interface BeerMapper {

    /**
     * Converts a BeerDTO object to a Beer entity.
     *
     * @param beerDTO The BeerDTO object to be converted.
     * @return The corresponding Beer entity.
     */
    Beer beerDTOToBeer(BeerDTO beerDTO);

    /**
     * Converts a Beer entity to a BeerDTO object.
     *
     * @param beer The Beer entity to be converted.
     * @return The corresponding BeerDTO object.
     */
    BeerDTO beerToBeerDTO(Beer beer);
}