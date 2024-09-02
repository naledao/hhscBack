package com.example.hhcs.controller;

import com.example.hhcs.dao.PayOrderDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.PayOrder;
import com.example.hhcs.domain.PayOrderStatus;
import com.example.hhcs.domain.User;
import com.example.hhcs.domain.WxReturn;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/wancheng")
public class WanChengController {
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
    @GetMapping
    private String text(@RequestParam("payId") String payId,@RequestParam("param") String param,@RequestParam("type") int type,@RequestParam("price") double price,@RequestParam("reallyPrice") double reallyPrice,@RequestParam("sign") String sign){
//        判断user是否存在
        String[] msg=param.split("[*]");
        String openid=msg[0];
        String choice=msg[1];
        if(choice.equals("s"))
        {
            User user= userDao.get(openid);
            if(user==null)
            {
                return "<h1>{code:0,msg:用户不存在}</h1>";
            }
            else
            {
//            判断订单是否支付
                PayOrder payOrder =payOrderDao.get(payId);
                if(payOrder==null)
                {
//                订单不存在
                    return "<h1>{code:1,msg:订单不存在}</h1>";
                }
                else
                {
                    String md=payOrder.getSecret();
                    if(md==null || md.equals(""))
                    {
                        String cloundid=payOrder.getCloundid();
//                检查订单支付情况
                        HttpClient httpClient = new HttpClient();
                        String url="http://47.100.9.232:9090/checkOrder?orderId="+cloundid;
                        GetMethod getMethod=new GetMethod(url);
                        try {
                            int num = httpClient.executeMethod(getMethod);
                            if(num==200)
                            {
                                String c=getMethod.getResponseBodyAsString();
                                PayOrderStatus stu = new Gson().fromJson(c, PayOrderStatus.class);
                                if(stu.getCode()==1)
                                {
//                            支付成功，生成key
                                    String[] strings=new String[]{"1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
                                    Random random=new Random(System.currentTimeMillis());
                                    StringBuilder stringBuilder=new StringBuilder();
                                    while (true)
                                    {
                                        for(int i=1;i<=6;i++)
                                        {
                                            stringBuilder.append(strings[random.nextInt(strings.length)]);
                                        }
                                        PayOrder payOrder1=payOrderDao.y(new String(stringBuilder));
                                        if(payOrder1==null)
                                        {
                                            break;
                                        }
                                        else
                                        {
                                            stringBuilder.delete(0,6);
                                        }
                                    }
                                    String key=new String(stringBuilder);
                                    payOrder.setSecret(key);
                                    payOrderDao.up(key,payId);
                                    stringRedisTemplate.delete("startzhifu:openid");
                                    stringRedisTemplate.delete("startzhifu:image");
                                    int day= (int) (price/3);
                                    stringRedisTemplate.opsForValue().set("key:"+key, String.valueOf(day),24, TimeUnit.HOURS);
                                    return "<h1>{code:3,msg:您的密匙为:"+key+" (可以到 我的-密匙记录 里面复制密匙)}</h1>";
                                }
                                else
                                {
                                    String warn=stu.getMsg();
                                    return "<h1>{code:3,msg:"+warn+"}</h1>";
                                }
                            }
                            else
                            {
                                return "<h1>{code:2,msg:网络错误}</h1>";
                            }
                        } catch (IOException e) {
                            return "<h1>{code:2,msg:网络错误}</h1>";
                        }
                    }
                    else
                    {
                        String key=payOrder.getSecret();
                        return "<h1>{code:3,msg:您的密匙为:"+key+" (可以到 我的-密匙记录 里面复制密匙)}</h1>";
                    }
                }
            }
        }


        if(choice.equals("a"))
        {
            User user= userDao.get(openid);
            if(user==null)
            {
                return "<h1>{code:0,msg:用户不存在}</h1>";
            }
            else
            {
//            判断订单是否支付
                PayOrder payOrder =payOrderDao.get(payId);
                if(payOrder==null)
                {
//                订单不存在
                    return "<h1>{code:1,msg:订单不存在}</h1>";
                }
                else
                {
                    String md=payOrder.getSecret();
                    if(md==null || md.equals(""))
                    {
                        String cloundid=payOrder.getCloundid();
//                检查订单支付情况
                        HttpClient httpClient = new HttpClient();
                        String url="http://47.100.9.232:9090/checkOrder?orderId="+cloundid;
                        GetMethod getMethod=new GetMethod(url);
                        try {
                            int num = httpClient.executeMethod(getMethod);
                            if(num==200)
                            {
                                String c=getMethod.getResponseBodyAsString();
                                PayOrderStatus stu = new Gson().fromJson(c, PayOrderStatus.class);
                                if(stu.getCode()==1)
                                {
//                            支付成功，生成key
                                    String[] strings=new String[]{"1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
                                    Random random=new Random(System.currentTimeMillis());
                                    StringBuilder stringBuilder=new StringBuilder();
                                    while (true)
                                    {
                                        for(int i=1;i<=6;i++)
                                        {
                                            stringBuilder.append(strings[random.nextInt(strings.length)]);
                                        }
                                        PayOrder payOrder1=payOrderDao.y(new String(stringBuilder));
                                        if(payOrder1==null)
                                        {
                                            break;
                                        }
                                        else
                                        {
                                            stringBuilder.delete(0,6);
                                        }
                                    }
                                    String key=new String(stringBuilder);
                                    payOrder.setSecret(key);
                                    payOrderDao.up(key,payId);
                                    stringRedisTemplate.delete("rollzhifu:openid");
                                    stringRedisTemplate.delete("rollzhifu:image");
                                    int day= (int) (price/2);
                                    stringRedisTemplate.opsForValue().set("akey:"+key, String.valueOf(day),24, TimeUnit.HOURS);
                                    return "<h1>{code:3,msg:您的密匙为:"+key+" (可以到 我的-密匙记录 里面复制密匙)}</h1>";
                                }
                                else
                                {
                                    String warn=stu.getMsg();
                                    return "<h1>{code:3,msg:"+warn+"}</h1>";
                                }
                            }
                            else
                            {
                                return "<h1>{code:2,msg:网络错误}</h1>";
                            }
                        } catch (IOException e) {
                            return "<h1>{code:2,msg:网络错误}</h1>";
                        }
                    }
                    else
                    {
                        String key=payOrder.getSecret();
                        return "<h1>{code:3,msg:您的密匙为:"+key+" (可以到 我的-密匙记录 里面复制密匙)}</h1>";
                    }
                }
            }
        }

        if(choice.equals("b"))
        {
            User user= userDao.get(openid);
            if(user==null)
            {
                return "<h1>{code:0,msg:用户不存在}</h1>";
            }
            else
            {
                //            判断订单是否支付
                PayOrder payOrder =payOrderDao.get(payId);
                if(payOrder==null)
                {
//                订单不存在
                    return "<h1>{code:1,msg:订单不存在}</h1>";
                }
                else
                {
                    String md=payOrder.getSecret();
                    if(md==null || md.equals(""))
                    {
                        String cloundid=payOrder.getCloundid();
//                检查订单支付情况
                        HttpClient httpClient = new HttpClient();
                        String url="http://47.100.9.232:9090/checkOrder?orderId="+cloundid;
                        GetMethod getMethod=new GetMethod(url);
                        try {
                            int num = httpClient.executeMethod(getMethod);
                            if(num==200)
                            {
                                String c=getMethod.getResponseBodyAsString();
                                PayOrderStatus stu = new Gson().fromJson(c, PayOrderStatus.class);
                                if(stu.getCode()==1)
                                {
//                            支付成功，生成key
                                    String[] strings=new String[]{"1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
                                    Random random=new Random(System.currentTimeMillis());
                                    StringBuilder stringBuilder=new StringBuilder();
                                    while (true)
                                    {
                                        for(int i=1;i<=6;i++)
                                        {
                                            stringBuilder.append(strings[random.nextInt(strings.length)]);
                                        }
                                        PayOrder payOrder1=payOrderDao.y(new String(stringBuilder));
                                        if(payOrder1==null)
                                        {
                                            break;
                                        }
                                        else
                                        {
                                            stringBuilder.delete(0,6);
                                        }
                                    }
                                    String key=new String(stringBuilder);
                                    payOrder.setSecret(key);
                                    payOrderDao.up(key,payId);
                                    Set<String> set=stringRedisTemplate.keys("slidezhifu:*");
                                    assert set != null;
                                    for(String i:set)
                                    {
                                        String nowopenid=stringRedisTemplate.opsForValue().get(i);
                                        if(nowopenid.equals(openid))
                                        {
                                            String time=i.split(":")[1];
                                            stringRedisTemplate.delete("slidezhifu:"+time+":openid");
                                            stringRedisTemplate.delete("slidezhifu:"+time+":image");
                                            break;
                                        }
                                    }
                                    int day= (int) (price/1);
                                    stringRedisTemplate.opsForValue().set("bkey:"+key, String.valueOf(day),24, TimeUnit.HOURS);
                                    return "<h1>{code:3,msg:您的密匙为:"+key+" (可以到 我的-密匙记录 里面复制密匙)}</h1>";
                                }
                                else
                                {
                                    String warn=stu.getMsg();
                                    return "<h1>{code:3,msg:"+warn+"}</h1>";
                                }
                            }
                            else
                            {
                                return "<h1>{code:2,msg:网络错误}</h1>";
                            }
                        } catch (IOException e) {
                            return "<h1>{code:2,msg:网络错误}</h1>";
                        }
                    }
                    else
                    {
                        String key=payOrder.getSecret();
                        return "<h1>{code:3,msg:您的密匙为:"+key+" (可以到 我的-密匙记录 里面复制密匙)}</h1>";
                    }
                }
            }
        }
        else
        {
            return "xsax";
        }
    }
}
