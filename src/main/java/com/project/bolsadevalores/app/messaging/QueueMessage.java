package com.project.bolsadevalores.app.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bolsadevalores.app.domain.Ordem;
import com.project.bolsadevalores.app.messaging.dto.AtualizacaoMessage;
import com.project.bolsadevalores.app.messaging.dto.OrdemMessage;
import com.project.bolsadevalores.app.service.BolsaDeValores;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QueueMessage {

    RabbitTemplate template;
    ObjectMapper objectMapper;

    //comprar.petr4
    //{"quantidade": 5, "valor": 135.5, "corretora": "XPTO"}
    @RabbitListener(queues = "ComprarAcao")
    public void receberCompraDeAcao(final OrdemMessage content,
                                    @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoDeAcao = routingKey.replaceFirst("comprar.", "");
        Ordem ordem = new Ordem(content.quantidade(), content.valor(), content.corretora(), codigoDeAcao);
        BolsaDeValores.comprarAcao(ordem, codigoDeAcao);
        System.out.println("Comprar: " + content);
    }

    @RabbitListener(queues = "VenderAcao")
    public void receberVendaDeAcao(final OrdemMessage content,
                                   @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        String codigoDeAcao = routingKey.replaceFirst("vender.", "");
        Ordem ordem = new Ordem(content.quantidade(), content.valor(), content.corretora(), codigoDeAcao);
        BolsaDeValores.venderAcao(ordem, codigoDeAcao);
        System.out.println("Vender: " + content);
    }

    public void enviarAtualizacaoDeStatus(AtualizacaoMessage atualizacao, String codigoAcao) {
        try {
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsString(atualizacao).getBytes())
                    .andProperties(MessagePropertiesBuilder
                            .newInstance()
                            .setContentType("application/json")
                            .build())
                    .build();
            template.convertAndSend("atualizacao.*", "atualizacao." + codigoAcao, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
