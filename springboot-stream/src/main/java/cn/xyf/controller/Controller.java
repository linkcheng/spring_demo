package cn.xyf.controller;

import cn.xyf.service.SenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class Controller {
    private SenderServiceImpl senderService;

    @Autowired
    public void setSenderService(SenderServiceImpl senderService) {
        this.senderService = senderService;
    }

    @RequestMapping("/send/{msg}")
    public String send(@PathVariable("msg") String msg) {
        senderService.send(msg);
        return "OK";
    }
}
