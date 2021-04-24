package io.github.hmnshgpt455.beerinventoryservice.services.listeners;

import io.github.hmnshgpt455.beerinventoryservice.config.JmsConfig;
import io.github.hmnshgpt455.beerinventoryservice.services.brewing.InventoryDeallocationService;
import io.github.hmnshgpt455.brewery.events.DeallocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeallocateInventoryRequestListener {

    private final InventoryDeallocationService inventoryDeallocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest deallocateOrderRequest) {
        inventoryDeallocationService.deAllocateInventory(deallocateOrderRequest.getBeerOrderDto());
    }
}
