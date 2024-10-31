package edu.du.sb1031.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentEvent {
    private Long shipmentId;
    private String productName;
    private int quantity;
    private double price;
    private String status;

    public ShipmentEvent(Long shipmentId, String productName, int quantity, double price, String status) {
        this.shipmentId = shipmentId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
}
