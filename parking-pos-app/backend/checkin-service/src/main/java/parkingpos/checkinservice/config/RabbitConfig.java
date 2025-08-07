package parkingpos.checkinservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String PARKING_EXCHANGE = "parking.exchange";
    public static final String CHECKIN_QUEUE = "checkin.queue";
    public static final String CHECKIN_ROUTING_KEY = "checkin.created";

    public static final String TICKET_RESPONSE_QUEUE = "ticket.response.queue";
    public static final String TICKET_RESPONSE_ROUTING_KEY = "ticket.created";


    @Bean
    public TopicExchange parkingExchange() {
        return new TopicExchange(PARKING_EXCHANGE);
    }

    @Bean
    public Queue checkinQueue() {
        return QueueBuilder.durable(CHECKIN_QUEUE).build()          ;
    }


    @Bean
    public Queue ticketResponseQueue() {
        return QueueBuilder.durable(TICKET_RESPONSE_QUEUE).build();
    }

    @Bean
    public Binding checkinBinding() {
        return BindingBuilder
        .bind(checkinQueue())
        .to(parkingExchange())
        .with(CHECKIN_ROUTING_KEY);
    }


    @Bean
    public Binding ticketResponseBinding() {
        return BindingBuilder
        .bind(ticketResponseQueue())
        .to(parkingExchange())
        .with(TICKET_RESPONSE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}

    
    