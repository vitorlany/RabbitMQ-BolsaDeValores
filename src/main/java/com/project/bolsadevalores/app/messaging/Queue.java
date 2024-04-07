package com.project.bolsadevalores.app.messaging;

import com.project.bolsadevalores.app.messaging.dto.OrdemMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Queue {

    @RabbitListener(queues = "ComprarAcao")
    public void comprarAcao(final OrdemMessage content) {
        System.out.println("Comprar: " + content);
    }

    @RabbitListener(queues = "VenderAcao")
    public void venderAcao(final OrdemMessage content) {
        System.out.println("Vender: " + content);
    }
}
