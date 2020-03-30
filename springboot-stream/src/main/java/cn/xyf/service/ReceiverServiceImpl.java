package cn.xyf.service;

import cn.xyf.common.Constants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = Constants.DEFAULT_QUEUE)
public class ReceiverServiceImpl {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("In receiver: receive msg: "+msg);
    }
}
