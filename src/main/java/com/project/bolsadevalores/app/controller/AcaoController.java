package com.project.bolsadevalores.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bolsadevalores.app.messaging.dto.OrdemMessage;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AcaoController {
    RabbitTemplate template;
    ObjectMapper objectMapper;

    @PostMapping("/comprar")
    void registrarCompra(@RequestBody AcaoRequest acaoRequest) throws JsonProcessingException {
        OrdemMessage ordemMessage = new OrdemMessage(acaoRequest.quantidade(), acaoRequest.valor(), acaoRequest.corretora());
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsString(ordemMessage).getBytes())
                .andProperties(MessagePropertiesBuilder
                        .newInstance()
                        .setContentType("application/json")
                        .build())
                .build();
        template.convertAndSend("comprar.*", "comprar." + acaoRequest.codigoAcao(), message);
    }

    @PostMapping("/vender")
    void registrarVenda(@RequestBody AcaoRequest acaoRequest) throws JsonProcessingException {
        OrdemMessage ordemMessage = new OrdemMessage(acaoRequest.quantidade(), acaoRequest.valor(), acaoRequest.corretora());
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsString(ordemMessage).getBytes())
                .andProperties(MessagePropertiesBuilder
                        .newInstance()
                        .setContentType("application/json")
                        .build())
                .build();
        template.convertAndSend("vender.*", "vender." + acaoRequest.codigoAcao(), message);
    }
}
