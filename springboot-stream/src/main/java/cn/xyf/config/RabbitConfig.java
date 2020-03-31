package cn.xyf.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.xyf.common.Constants.*;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE1, true);
    }

    @Bean
    public Queue queue() {
        return new Queue(DEFAULT_QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding binding1(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY1);
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1, true);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2, true);
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bindingExchange1(Queue fanoutQueue1, FanoutExchange exchange) {
        return BindingBuilder.bind(fanoutQueue1).to(exchange);
    }

    @Bean
    public Binding bindingExchange2(Queue fanoutQueue2, FanoutExchange exchange) {
        return BindingBuilder.bind(fanoutQueue2).to(exchange);
    }
}
