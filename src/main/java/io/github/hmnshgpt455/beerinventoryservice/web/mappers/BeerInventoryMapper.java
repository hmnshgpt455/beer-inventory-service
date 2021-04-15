package io.github.hmnshgpt455.beerinventoryservice.web.mappers;

import io.github.hmnshgpt455.beerinventoryservice.domain.BeerInventory;
import io.github.hmnshgpt455.brewery.model.BeerInventoryDto;
import org.mapstruct.Mapper;

/**
 * Created by jt on 2019-05-31.
 */
@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

    BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
