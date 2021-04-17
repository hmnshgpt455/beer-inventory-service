package io.github.hmnshgpt455.beerinventoryservice.services.listeners;

import io.github.hmnshgpt455.beerinventoryservice.config.JmsConfig;
import io.github.hmnshgpt455.brewery.events.InventoryEvent;
import io.github.hmnshgpt455.beerinventoryservice.services.InventoryService;
import io.github.hmnshgpt455.brewery.model.BeerDTO;
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
