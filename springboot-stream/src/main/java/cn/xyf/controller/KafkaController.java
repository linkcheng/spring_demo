package cn.xyf.controller;

import cn.xyf.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private KafkaService kafkaService;

    @Autowired
    public void setKafkaService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    /**
     * 创建主题
     * /opt/confluent/bin/kafka-topics --create --topic topic1 --partitions 6  --replication-factor 1  --bootstrap-server 192.168.0.44:9092
     */
    @GetMapping("/send/{topic}/{data}")
    public String send(@PathVariable("topic") String topic, @PathVariable("data") String data) {
        kafkaService.sendMessage(topic, data);
        return "OK";
    }
}
