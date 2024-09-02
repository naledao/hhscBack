package com.example.hhcs.handler;


import com.example.hhcs.util.MailUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
public class ListenQueue {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RabbitListener(queues = "email_queue")
    public void process(Message message, Channel channel) throws IOException {
        byte[] body = message.getBody();
        String[] msg=new String(body).split(",");
//        0:邮箱地址,1:验证码,2:邮件标题
        MailUtils.sendMail(msg[0],msg[1],msg[2]);
        stringRedisTemplate.opsForValue().set("sms:"+msg[0],msg[1].substring(6),60, TimeUnit.SECONDS);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
