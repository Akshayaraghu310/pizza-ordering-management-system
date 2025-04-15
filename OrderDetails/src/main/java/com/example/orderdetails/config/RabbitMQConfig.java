package com.example.orderdetails.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "pizzaExchange";
    public static final String ORDER_SERVICE_NOTIFICATION_QUEUE = "orderService.notificationQueue";

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Bean
    public TopicExchange exchange() {
        log.info("Initializing exchange: " + EXCHANGE);
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue orderServiceNotificationQueue() {
        log.info("Creating queue: " + ORDER_SERVICE_NOTIFICATION_QUEUE);
        return new Queue(ORDER_SERVICE_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue orderServiceNotificationQueue, TopicExchange exchange) {
        log.info("Binding queue to exchange with routing key: 'payment.success'");
        return BindingBuilder.bind(orderServiceNotificationQueue).to(exchange).with("payment.success");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        log.info("RabbitTemplate initialized successfully.");
        return template;
    }
}

