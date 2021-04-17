package io.github.hmnshgpt455.beerinventoryservice.services.brewing;

import io.github.hmnshgpt455.beerinventoryservice.domain.BeerInventory;
import io.github.hmnshgpt455.beerinventoryservice.exceptions.InventoryAllocationException;
import io.github.hmnshgpt455.beerinventoryservice.repositories.BeerInventoryRepository;
import io.github.hmnshgpt455.brewery.model.BeerOrderDto;
import io.github.hmnshgpt455.brewery.model.BeerOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryAllocationServiceImpl implements InventoryAllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateInventoryToOrder(BeerOrderDto beerOrder) throws InventoryAllocationException {

        try {
            AtomicInteger totalQuantityOrdered = new AtomicInteger();
            AtomicInteger totalQuantityAllocated = new AtomicInteger();

            log.debug("Allocating inventory for order with id " + beerOrder.getId());
            Optional.ofNullable(beerOrder.getBeerOrderLines()).ifPresent(orderLines -> orderLines.forEach(orderLine -> {
                Integer orderQuantity = Optional.ofNullable(orderLine.getOrderQuantity()).orElseGet(() -> {
                    orderLine.setOrderQuantity(0);
                    return 0;
                });
                Integer allocatedQuantity = Optional.ofNullable(orderLine.getQuantityAllocated()).orElseGet(() -> {
                    orderLine.setQuantityAllocated(0);
                    return 0;
                });

                if (allocatedQuantity < orderQuantity) {
                    allocateInventoryToOrderLine(orderLine);
                }

                totalQuantityAllocated.addAndGet(orderLine.getQuantityAllocated());
                totalQuantityOrdered.addAndGet(orderLine.getOrderQuantity());

            }));

            return totalQuantityAllocated.get() == totalQuantityOrdered.get();
        } catch (Exception e) {
            throw new InventoryAllocationException();
        }


    }

    private void allocateInventoryToOrderLine(BeerOrderLineDto orderLine) {
        List<BeerInventory> beerInventories = beerInventoryRepository.findAllByUpc(orderLine.getUpc());

        beerInventories.forEach(beerInventory -> {
            Integer quantityOnHand = Optional.ofNullable(beerInventory.getQuantityOnHand()).orElse(0);
            Integer orderQuantity = orderLine.getOrderQuantity();
            Integer allocatedQuantity = orderLine.getQuantityAllocated();
            int quantityToAllocate = orderQuantity - allocatedQuantity;

            if (quantityToAllocate > 0) {
                if (quantityOnHand >= quantityToAllocate) { //Fully allocated
                    orderLine.setQuantityAllocated(orderQuantity);
                    beerInventory.setQuantityOnHand(quantityOnHand-orderQuantity);
                    beerInventoryRepository.save(beerInventory);
                } else { //Partially allocated
                    orderLine.setQuantityAllocated(allocatedQuantity + quantityOnHand);
                    beerInventory.setQuantityOnHand(0);
                    beerInventoryRepository.delete(beerInventory);
                }
            }

        });
    }
}
