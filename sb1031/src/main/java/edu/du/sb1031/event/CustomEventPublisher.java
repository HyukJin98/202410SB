package edu.du.sb1031.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void doStuffAndPublishAnEvent(final String message){
        System.out.println("Publishing custom event.");
        CustomEvent customSpringEvent = new CustomEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

    // 주문 이벤트를 발행하는 메서드
    public void publishOrderEvent(OrderEvent orderEvent) {
        System.out.println("Publishing order event.");
        applicationEventPublisher.publishEvent(orderEvent);
    }


}
