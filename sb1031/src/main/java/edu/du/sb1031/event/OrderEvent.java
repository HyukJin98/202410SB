package edu.du.sb1031.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private Long orderId;
    private String productName;
    private int quantity;
    private double price;

    public OrderEvent(Long orderId, String productName, int quantity, double price) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
