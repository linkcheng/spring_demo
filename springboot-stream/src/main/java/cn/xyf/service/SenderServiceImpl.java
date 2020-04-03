package cn.xyf.service;

import cn.xyf.common.Constants;
import cn.xyf.dto.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class SenderServiceImpl {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void initTemplate() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
            if(!ack){
                log.error("correlationData："+ correlationData);
                log.error("cause："+cause);
            } else {
                log.info("ok");
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("返回消息配置："+message.getMessageProperties().toString());
            log.error("反馈代码："+replyCode);
            log.error("反馈内容："+replyText);
            log.error("exchange："+exchange);
            log.error("routingKey："+routingKey);
        });
    }

    public void send(String msg) {
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.DEFAULT_ROUTING_KEY, msg);
    }

    public void sendMessage(RabbitMessage msg) {
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.DEFAULT_ROUTING_KEY, msg);
    }

    public void sendMessage1(RabbitMessage msg) {
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY1, msg);
    }

    public void sendMessageFanout(RabbitMessage msg) {
        rabbitTemplate.convertAndSend(Constants.FANOUT_EXCHANGE, "", msg);
    }
}
