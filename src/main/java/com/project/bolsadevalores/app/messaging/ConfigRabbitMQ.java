package com.project.bolsadevalores.app.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class ConfigRabbitMQ {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Queue queueVenderAcao() {
        return new Queue("VenderAcao", true);
    }

    @Bean
    TopicExchange exchangeVender() {
        return new TopicExchange("vender.*");
    }

    @Bean
    Binding bindingVender() {
        return BindingBuilder.bind(queueVenderAcao()).to(exchangeVender()).with("vender.*");
    }

    @Bean
    Queue queueComprarAcao() {
        return new Queue("ComprarAcao", true);
    }

    @Bean
    TopicExchange exchangeComprar() {
        return new TopicExchange("comprar.*");
    }

    @Bean
    Binding bindingComprar() {
        return BindingBuilder.bind(queueComprarAcao()).to(exchangeComprar()).with("comprar.*");
    }

    @Bean
    Queue queueAtualizacaoAcao() {
        return new Queue("AtualizacaoAcao", true);
    }


    @Bean
    TopicExchange exchangeAtualizacao() {
        return new TopicExchange("atualizacao.*");
    }

    @Bean
    Binding bindingAtualizacao() {
        return BindingBuilder.bind(queueAtualizacaoAcao()).to(exchangeAtualizacao()).with("atualizacao.*");
    }

}
