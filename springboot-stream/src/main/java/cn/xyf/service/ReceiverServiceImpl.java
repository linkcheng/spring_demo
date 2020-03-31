package cn.xyf.service;

import cn.xyf.common.Constants;
import cn.xyf.pojo.RabbitMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiverServiceImpl {
    @RabbitListener(queues = Constants.DEFAULT_QUEUE)
    public void process(Object msg) {
        System.out.println("In default queue receiver: receive msg: "+msg);
    }

    @RabbitListener(queues = Constants.QUEUE1)
    public void process1(RabbitMessage msg) {
        System.out.println("In queue1 receiver: receive msg: "+msg);
    }

    @RabbitListener(queues = {Constants.FANOUT_QUEUE1, Constants.FANOUT_QUEUE2})
    public void processFanout(RabbitMessage msg) {
        System.out.println("In fanout receiver: receive msg: "+msg);
    }
}
