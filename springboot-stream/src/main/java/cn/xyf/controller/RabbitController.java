package cn.xyf.controller;

import cn.xyf.dto.RabbitMessage;
import cn.xyf.service.SenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    private SenderServiceImpl senderService;

    @Autowired
    public void setSenderService(SenderServiceImpl senderService) {
        this.senderService = senderService;
    }

    @GetMapping("/send/{msg}")
    public String send(@PathVariable("msg") String msg) {
        senderService.send(msg);
        return "OK";
    }

    @PostMapping("/sendMsg")
    public String sendMsg(@RequestBody RabbitMessage msg) {
        System.out.println(msg);
        senderService.sendMessage(msg);
        return "OK";
    }

    @PostMapping("/sendMsg1")
    public String sendMsg1(@RequestBody RabbitMessage msg) {
        senderService.sendMessage1(msg);
        return "OK";
    }

    @PostMapping("/sendMsgFanout")
    public String sendMsgFanout(@RequestBody RabbitMessage msg) {
        senderService.sendMessageFanout(msg);
        return "OK";
    }
}
