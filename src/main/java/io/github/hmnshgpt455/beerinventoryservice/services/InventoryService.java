package io.github.hmnshgpt455.beerinventoryservice.services;

import io.github.hmnshgpt455.brewery.model.BeerDTO;
import io.github.hmnshgpt455.brewery.model.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<BeerInventoryDto> getInventoryByBeerId(UUID beerId);

    List<BeerInventoryDto> getInventoryByBeerUPC(String upc);

    void updateBeerInventory(BeerDTO beerDTO);

}
