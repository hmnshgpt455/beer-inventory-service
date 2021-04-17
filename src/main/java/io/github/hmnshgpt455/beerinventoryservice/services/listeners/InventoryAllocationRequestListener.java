package io.github.hmnshgpt455.beerinventoryservice.services.listeners;

import io.github.hmnshgpt455.beerinventoryservice.config.JmsConfig;
import io.github.hmnshgpt455.brewery.events.AllocateBeerOrderRequest;
import io.github.hmnshgpt455.brewery.events.AllocateBeerOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryAllocationRequestListener {

    private final InventoryAllocator inventoryAllocator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateBeerOrderRequest allocateBeerOrderRequest) {
        AllocateBeerOrderResult result = inventoryAllocator.allocate(allocateBeerOrderRequest.getBeerOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE, result);
    }
}
