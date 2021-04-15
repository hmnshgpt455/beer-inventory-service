package io.github.hmnshgpt455.beerinventoryservice.web.controllers;

import io.github.hmnshgpt455.beerinventoryservice.services.InventoryService;
import io.github.hmnshgpt455.brewery.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-05-31.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(BeerInventoryController.API_V1_BEER)
public class BeerInventoryController {

    public static final String API_V1_BEER = "/api/v1/beer";
    private final InventoryService inventoryService;

    @GetMapping("/{beerId}/inventory")
    List<BeerInventoryDto> listBeersById(@PathVariable UUID beerId){
        log.debug("Finding Inventory for beerId:" + beerId);

        return inventoryService.getInventoryByBeerId(beerId);
    }

    @GetMapping( "/upc/{upc}/inventory")
    List<BeerInventoryDto> listBeersByUPC(@PathVariable String upc){
        log.debug("Finding Inventory for beerId:" + upc);

        return inventoryService.getInventoryByBeerUPC(upc);
    }
}
