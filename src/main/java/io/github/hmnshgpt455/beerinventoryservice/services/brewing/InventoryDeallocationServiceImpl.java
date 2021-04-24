package io.github.hmnshgpt455.beerinventoryservice.services.brewing;

import io.github.hmnshgpt455.beerinventoryservice.domain.BeerInventory;
import io.github.hmnshgpt455.beerinventoryservice.repositories.BeerInventoryRepository;
import io.github.hmnshgpt455.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryDeallocationServiceImpl implements InventoryDeallocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void deAllocateInventory(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
                    .build();

            BeerInventory savedBeerInventory = beerInventoryRepository.save(beerInventory);

            log.debug("Saved inventory for beer upc " + savedBeerInventory.getUpc() + " inventory id : " +
                    savedBeerInventory.getId() + " quantity : " + savedBeerInventory.getQuantityOnHand());
        });
    }
}
