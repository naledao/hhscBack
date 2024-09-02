package com.example.hhcs.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitConfig {
    // 定义交换机
    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange("myExchange",true,false);
    }

    // chatgpt消息通知队列
    @Bean
    public Queue chatgpt_queue() {
        return new Queue("chatgpt_queue",true,false,false);
    }

    @Bean
    public Binding binding1(Queue chatgpt_queue, DirectExchange myExchange) {
        return BindingBuilder.bind(chatgpt_queue).to(myExchange).with("chatgpt");
    }

    // 商品留言队列
    @Bean
    public Queue leavemsg_queue() {
        return new Queue("leavemsg_queue",true,false,false);
    }


    @Bean
    public Binding binding2(Queue leavemsg_queue, DirectExchange myExchange) {
        return BindingBuilder.bind(leavemsg_queue).to(myExchange).with("leavemsg");
    }

    //绑定邮箱验证码队列
    @Bean
    public Queue email_queue(){
        return new Queue("email_queue",true,false,false);
    }

    @Bean
    public Binding binding3(Queue email_queue,DirectExchange myExchange){
        return BindingBuilder.bind(email_queue).to(myExchange).with("email");
    }
}
