package edu.du.sb1031.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
public class CustomEventListener {
    @Autowired
    private ApplicationEventPublisher publisher;

    @EventListener
    public void handleCustomEvent(CustomEvent customEvent) {
        log.info("Handling context started event. message:{}", customEvent.getMessage());
    }

    @EventListener
    public void handleOrderEvent(OrderEvent orderEvent) {
        log.info("Handling order event. Order ID: {}, Product: {}, Quantity: {}, Price: {}",
                orderEvent.getOrderId(), orderEvent.getProductName(), orderEvent.getQuantity(), orderEvent.getPrice());

        // 배송 이벤트 생성
        ShipmentEvent shipmentEvent = new ShipmentEvent(
                orderEvent.getOrderId(),
                orderEvent.getProductName(),
                orderEvent.getQuantity(),
                orderEvent.getPrice(),
                "Preparing for shipment" // 배송 상태 초기화
        );
        publisher.publishEvent(shipmentEvent);
    }

    @EventListener
    public void handleShipmentEvent(ShipmentEvent shipmentEvent) {
        log.info("Handling shipment event. Shipment ID: {}, Product: {}, Quantity: {}, Price: {}, Status: {}",
                shipmentEvent.getShipmentId(), shipmentEvent.getProductName(), shipmentEvent.getQuantity(),
                shipmentEvent.getPrice(), shipmentEvent.getStatus());
    }



}
