package com.refreshing.beer.services;

import com.refreshing.beer.web.model.BeerDTO;
import com.refreshing.beer.web.model.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<BeerInventoryDto> getInventoryByBeerId(UUID beerId);

    List<BeerInventoryDto> getInventoryByBeerUPC(String upc);

    void updateBeerInventory(BeerDTO beerDTO);

}
