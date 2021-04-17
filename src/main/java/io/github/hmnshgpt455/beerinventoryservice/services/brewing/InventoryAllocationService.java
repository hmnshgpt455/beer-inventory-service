package io.github.hmnshgpt455.beerinventoryservice.services.brewing;

import io.github.hmnshgpt455.brewery.model.BeerOrderDto;

public interface InventoryAllocationService {

    Boolean allocateInventoryToOrder(BeerOrderDto beerOrderDto);
}
