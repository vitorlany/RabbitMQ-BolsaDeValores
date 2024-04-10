package com.project.bolsadevalores.app.messaging;

import com.project.bolsadevalores.app.domain.Ordem;
import com.project.bolsadevalores.app.messaging.dto.OrdemMessage;
import com.project.bolsadevalores.app.service.BolsaDeValores;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Queue {

    RabbitTemplate template;

    //comprar.petr4
    //{"quantidade": 5, "valor": 135.5, "corretora": "XPTO"}
    @RabbitListener(queues = "ComprarAcao")
    public void receberCompraDeAcao(final OrdemMessage content,
                                    @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoDeAcao = routingKey.replaceFirst("comprar.", "");
        Ordem ordem = new Ordem(content.quantidade(), content.valor(), content.corretora());
        BolsaDeValores.comprarAcao(ordem, codigoDeAcao);
        System.out.println("Comprar: " + content);
    }

    @RabbitListener(queues = "VenderAcao")
    public void receberVendaDeAcao(final OrdemMessage content,
                                   @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoDeAcao = routingKey.replaceFirst("vender.", "");
        Ordem ordem = new Ordem(content.quantidade(), content.valor(), content.corretora());
        BolsaDeValores.venderAcao(ordem, codigoDeAcao);
        System.out.println("Vender: " + content);
    }

    @PostConstruct
    public void enviarAtualizacaoDeStatus() {
        OrdemMessage ordem = new OrdemMessage(1, 10, "abc");
        template.convertAndSend("comprar.*", "comprar.petr4", ordem);
    }
}
