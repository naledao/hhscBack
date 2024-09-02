package com.example.hhcs.controller;

import com.example.hhcs.dao.EmailDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import com.example.hhcs.util.EmailVali;
import com.example.hhcs.util.MailUtils;
import com.example.hhcs.util.SMS;
import org.apache.ibatis.annotations.Delete;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private EmailDao emailDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    检查是否绑定邮箱
    @GetMapping("/check/{openid}")
    private Result getemail(@PathVariable String openid)
    {
        String email=emailDao.getemail(openid);
        if(email==null || email.equals(""))
        {
            return new Result(0,"");
        }
        else
        {
            return new Result(1,email);
        }
    }



//    获取邮箱验证码
    @PostMapping("/getsms")
    private int getsms(@RequestParam("email") String email)
    {
        if(email.length()>30)
        {
            return 0;
        }
        else
        {
            if(EmailVali.legal(email))
            {
                // 生成验证码
                String sms= SMS.sms();
                String msg="你的验证码为 "+sms;
                rabbitTemplate.convertAndSend("myExchange","email",email+","+msg+","+"黄淮市场绑定邮箱验证码");
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }




//    验证邮箱
    @PostMapping("/bindemail/{sms}/{openid}")
    private int bindemail(@PathVariable String sms,@RequestParam("email") String email,@PathVariable String openid)
    {
        String code=stringRedisTemplate.opsForValue().get("sms:"+email);
        if(code==null)
        {
            return 0;
        }
        else
        {
            if(code.equals(sms))
            {
                User user= userDao.get(openid);
                if(user==null)
                {
                    return 0;
                }
                else
                {
                    int res=emailDao.upemail(email,openid);
                    if(res==1)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
            else
            {
                return 0;
            }
        }
    }



//    删除邮箱
    @DeleteMapping("/{openid}")
    private int delemail(@PathVariable String openid)
    {
        User user= userDao.get(openid);
        if(user==null)
        {
            return 0;
        }
        else
        {
            int res=emailDao.delemail("",openid);
            if(res==1)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
}
