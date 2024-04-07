package com.project.bolsadevalores.app.messaging;

import com.project.bolsadevalores.app.domain.Ordem;
import com.project.bolsadevalores.app.messaging.dto.OrdemMessage;
import com.project.bolsadevalores.app.service.BolsaDeValores;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class Queue {

    //comprar.petr4
    //{"quantidade": 5, "valor": 135.5, "corretora": "XPTO"}
    @RabbitListener(queues = "ComprarAcao")
    public void comprarAcao(final OrdemMessage content,
                            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoDeAcao = routingKey.replaceFirst("comprar.", "");
        Ordem ordem = new Ordem(content.quantidade(), content.valor(), content.corretora());
        BolsaDeValores.comprarAcao(ordem, codigoDeAcao);
    }

    @RabbitListener(queues = "VenderAcao")
    public void venderAcao(final OrdemMessage content,
                           @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoDeAcao = routingKey.replaceFirst("vender.", "");
        Ordem ordem = new Ordem(content.quantidade(), content.valor(), content.corretora());
        BolsaDeValores.venderAcao(ordem, codigoDeAcao);
    }
}
