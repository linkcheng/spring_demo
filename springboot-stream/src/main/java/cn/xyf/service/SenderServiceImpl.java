package cn.xyf.service;

import cn.xyf.common.Constants;
import cn.xyf.pojo.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderServiceImpl {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String msg) {
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.DEFAULT_ROUTING_KEY, msg);
    }

    public void sendMessage(Message msg) {
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.DEFAULT_ROUTING_KEY, msg);
    }

    public void sendMessage1(Message msg) {
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY1, msg);
    }

    public void sendMessageFanout(Message msg) {
        rabbitTemplate.convertAndSend(Constants.FANOUT_EXCHANGE, "", msg);
    }
}
