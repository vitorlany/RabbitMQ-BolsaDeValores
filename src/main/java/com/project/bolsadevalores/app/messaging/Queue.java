package com.project.bolsadevalores.app.messaging;

import com.project.bolsadevalores.app.messaging.dto.OrdemMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class Queue {

    @RabbitListener(queues = "ComprarAcao")
    public void comprarAcao(final OrdemMessage content,
                            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoAcao = routingKey.replaceFirst("comprar.", "");
        System.out.println("Comprar: " + content);
        System.out.println("Key: " + codigoAcao);
    }

    @RabbitListener(queues = "VenderAcao")
    public void venderAcao(final OrdemMessage content,
                           @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoAcao = routingKey.replaceFirst("vender.", "");
        System.out.println("Vender: " + content);
        System.out.println("Key: " + codigoAcao);
    }
}
