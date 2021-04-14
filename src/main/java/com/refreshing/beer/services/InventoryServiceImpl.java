package com.refreshing.beer.services;

import com.refreshing.beer.domain.BeerInventory;
import com.refreshing.beer.repositories.BeerInventoryRepository;
import com.refreshing.beer.web.mappers.BeerInventoryMapper;
import com.refreshing.beer.web.model.BeerDTO;
import com.refreshing.beer.web.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @Override
    public List<BeerInventoryDto> getInventoryByBeerId(UUID beerId) {
        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BeerInventoryDto> getInventoryByBeerUPC(String upc) {
        return beerInventoryRepository.findAllByUpc(upc)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBeerInventory(BeerDTO beerDTO) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerDTO.getUpc());
        if (beerInventoryList.size() == 0) {
            beerInventoryRepository.save(BeerInventory.builder()
                                            .beerId(beerDTO.getId())
                                            .upc(beerDTO.getUpc())
                                            .quantityOnHand(beerDTO.getQuantityOnHand())
                                            .build());
        } else {
            Integer totalQuantityOnHand = beerInventoryList.stream()
                    .mapToInt(BeerInventory::getQuantityOnHand)
                    .sum();
            BeerInventory beerInventoryToUpdate = beerInventoryList.get(0);
            beerInventoryToUpdate.setQuantityOnHand(beerInventoryToUpdate.getQuantityOnHand() + (beerDTO.getQuantityOnHand()-totalQuantityOnHand));
            beerInventoryRepository.save(beerInventoryToUpdate);
        }
    }
}
