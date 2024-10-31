package edu.du.sb1031.order;

import edu.du.sb1031.event.CustomEventPublisher;
import edu.du.sb1031.event.OrderEvent;
import edu.du.sb1031.shipment.Shipment; // 배송 엔티티 추가
import edu.du.sb1031.shipment.ShipmentService; // 배송 서비스 추가
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CustomEventPublisher customEventPublisher;
    private final OrderService orderService;
    private final ShipmentService shipmentService; // 배송 서비스 주입

    @PostMapping
    @ResponseBody
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "/orders/orderList";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("orders", new Order());
        return "/orders/orderForm";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order) {
        log.info("Order created: " + order);

        // 주문 저장
        Order savedOrder = orderService.saveOrder(order);

        // 주문 이벤트 발행
        OrderEvent orderEvent = new OrderEvent(savedOrder.getId(), savedOrder.getProductName(), savedOrder.getQuantity(), savedOrder.getPrice());
        customEventPublisher.publishOrderEvent(orderEvent);

        // 배송 엔티티 생성 및 저장
        Shipment shipment = new Shipment();
        shipment.setProductName(savedOrder.getProductName());
        shipment.setQuantity(savedOrder.getQuantity());
        shipment.setPrice(savedOrder.getPrice());
        shipment.setStatus("준비중"); // 초기 상태 설정
        shipmentService.saveShipment(shipment); // 배송 정보 저장

        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder1(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
