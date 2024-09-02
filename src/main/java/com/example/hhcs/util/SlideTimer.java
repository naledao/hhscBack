package com.example.hhcs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SlideTimer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Scheduled(cron = "0 0 */1 * * ?")
    public void check()
    {
        //    @Scheduled(cron = "*/9 * * * * ?")
//        MailUtils.sendMail("2419646091@qq.com","定时器","【黄淮市场】定时器");
        List<String> list = stringRedisTemplate.opsForList().range("slide",0,-1);
        List<String> list1=new ArrayList<>();
        if(list!=null && list.size()!=0)
        {
            for(String i:list)
            {
                String key=i.split(":")[0];
                if(stringRedisTemplate.opsForValue().get("timeslide:"+key)!=null)
                {
                    list1.add(i);
                }
            }
            stringRedisTemplate.opsForValue().set("slideweixin:"+3,"3",6, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("slideweixin:"+2,"2",6, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("slideweixin:"+1,"1",6, TimeUnit.SECONDS);
            Boolean b = stringRedisTemplate.delete("slide");
            if(b)
            {
                for(String i:list1)
                {
                    stringRedisTemplate.opsForList().leftPush("slide",i);
                }
            }
        }
    }
}
