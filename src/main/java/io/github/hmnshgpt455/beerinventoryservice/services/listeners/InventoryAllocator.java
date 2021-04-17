package io.github.hmnshgpt455.beerinventoryservice.services.listeners;

import io.github.hmnshgpt455.beerinventoryservice.exceptions.InventoryAllocationException;
import io.github.hmnshgpt455.beerinventoryservice.services.brewing.InventoryAllocationService;
import io.github.hmnshgpt455.brewery.events.AllocateBeerOrderResult;
import io.github.hmnshgpt455.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryAllocator {

    private final InventoryAllocationService inventoryAllocationService;

    public AllocateBeerOrderResult allocate(BeerOrderDto beerOrderDto) {
        try {
            Boolean isAllocationComplete = inventoryAllocationService.allocateInventoryToOrder(beerOrderDto);
            return buildAllocationResult(beerOrderDto, isAllocationComplete, false);
        } catch (InventoryAllocationException e) {
            log.debug("Allocation failed for the order : " + beerOrderDto.getId());
            return buildAllocationResult(beerOrderDto, false, true);
        }
    }

    private AllocateBeerOrderResult buildAllocationResult(BeerOrderDto beerOrderDto, Boolean isAllocationComplete, Boolean isAllocationFailed) {

        return AllocateBeerOrderResult.builder()
                .isAllocationComplete(isAllocationComplete)
                .isAllocationFailed(isAllocationFailed)
                .beerOrder(beerOrderDto)
                .build();
    }

}
