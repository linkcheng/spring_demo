package cn.xyf.controller;

import cn.xyf.dto.CommonMessage;
import cn.xyf.dto.CommonResult;
import cn.xyf.dto.KafkaMessage;
import cn.xyf.dto.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/remote")
@Slf4j
public class RemoteController {
    private final static String URL = "http://39.106.251.31:9999/";
    private RestTemplate restTemplate;

    @Resource(name = "restTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/msg")
    public CommonResult send(@RequestBody CommonMessage msg) {
        log.info(msg.toString());
        String destination = msg.getDestination();
        String url = URL + destination + "/sendMsg";
        log.info(url);
        String ret;

        if(destination.equals("kafka")) {
            ret = restTemplate.postForObject(url, new KafkaMessage(msg.getTitle(), msg.getContent()), String.class);
        } else {
            ret = restTemplate.postForObject(url, new RabbitMessage(msg.getTitle(), msg.getContent()), String.class);
        }
        return new CommonResult<>(200, "发送成功", ret);
    }

    @GetMapping("/msg")
    public String read() {
        return "OK";
    }
}
