package com.example.springpracticereactive.mappers;

import com.example.springpracticereactive.domain.Beer;
import com.example.springpracticereactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

	Beer beerDTOToBeer(BeerDTO beerDTO);

	BeerDTO beerToBeerDTO(Beer beer);
}
