package cn.xyf.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RabbitMessage implements Serializable {
    private String title;
    private String content;
}
