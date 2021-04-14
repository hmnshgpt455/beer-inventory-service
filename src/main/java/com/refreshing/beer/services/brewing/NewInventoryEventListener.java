package com.refreshing.beer.services.brewing;

import com.refreshing.beer.config.JmsConfig;
import com.refreshing.beer.events.InventoryEvent;
import com.refreshing.beer.services.InventoryService;
import com.refreshing.beer.web.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NewInventoryEventListener {

    private final InventoryService inventoryService;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(InventoryEvent inventoryEvent) {
        BeerDTO beerDTO = inventoryEvent.getBeerDTO();
        log.debug("Got new inventory event for beer upc : " + beerDTO.getUpc());
        inventoryService.updateBeerInventory(beerDTO);
    }
}
