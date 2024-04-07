package com.project.bolsadevalores.app.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Queue {

    @RabbitListener(queues = "ComprarAcao")
    public void comprarAcao(String content) {
        System.out.println("Comprar: " + content);
    }

    @RabbitListener(queues = "VenderAcao")
    public void venderAcao(String content) {
        System.out.println("Vender: " + content);
    }
}
