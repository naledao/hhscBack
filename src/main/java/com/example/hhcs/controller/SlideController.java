package com.example.hhcs.controller;

import com.example.hhcs.dao.BstoreDao;
import com.example.hhcs.dao.PayOrderDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.*;
import com.example.hhcs.util.MD5Util;
import com.example.hhcs.util.SlideZhanYou;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("slide")
public class SlideController {
    @Autowired
    private SlideZhanYou slideZhanYou;
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    确认抢占
    @PostMapping("/{openid}/{price}")
    private synchronized Result post(@PathVariable String openid,@PathVariable String price)
    {
        User user= userDao.get(openid);
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String x = df.format(day);
        String time2=stringRedisTemplate.opsForValue().get("QQtime");
        if(time2==null)
        {
            return new Result(0,"");
        }
        if(user==null ||  (Integer.parseInt(x)!=Integer.parseInt(time2)))
        {
            System.out.println(0);
//            订单创建失败
            return new Result(11,"");
        }
//        判断是否超时
        Set<String> set=stringRedisTemplate.keys("slideweixin:*");
        if(set==null || set.size()==0)
        {
//            操作超时
            return new Result(10,"");
        }
        else
        {
            for(String i:set)
            {
                String nowopenid=stringRedisTemplate.opsForValue().get(i);
                if(nowopenid!=null && nowopenid.equals(openid))
                {
//                    创建订单
//                    设置创建订单最大时间
                    String time=System.currentTimeMillis()+openid;
                    stringRedisTemplate.opsForValue().set("slideroom:"+time,openid,10,TimeUnit.SECONDS);
//                    开始创建订单
                    String payid = openid+System.currentTimeMillis();
                    int type = 1;
                    String param = openid+"*b";
                    String mishi = "6a51b1289972e2c16fed2de81b2f6e64";
                    int isHtml = 0;
                    String sign = MD5Util.getMd5(payid + param + type + price + mishi);
                    HttpClient httpClient = new HttpClient();
                    String url="http://47.100.9.232:9090/createOrder?payId=" + payid + "&type=" + type +
                            "&price=" + price + "&sign=" + sign + "&param=" + param + "&isHtml=" + isHtml;
                    GetMethod getMethod = new GetMethod(url);
                    try {
                        int num = httpClient.executeMethod(getMethod);
                        if (num == 200)
                        {
                            String c = getMethod.getResponseBodyAsString();
                            Pay stu = new Gson().fromJson(c, Pay.class);
                            if (stu.getCode() == 1)
                            {
                                long time1=System.currentTimeMillis();
                                stringRedisTemplate.opsForValue().set("slidezhifu:"+time1+":image",stu.getData().getOrderId(),295,TimeUnit.SECONDS);
                                stringRedisTemplate.opsForValue().set("slidezhifu:"+time1+":openid",openid,295,TimeUnit.SECONDS);
                                PayOrder payOrder=new PayOrder();
                                payOrder.setPayid(String.valueOf(payid));
                                payOrder.setCloundid(stu.getData().getOrderId());
                                payOrder.setOpenid(openid);
                                payOrderDao.add(payOrder);
                                return new Result(12, stu.getData().getOrderId());
                            }
                            else
                            {
                                System.out.println(1);
//                                创建订单失败
                                return new Result(11,"");
                            }
                        }
                    } catch (IOException e) {
//                        创建订单失败
                        return new Result(11,"");
                    }
                }
            }
//            操作超时
            return new Result(10,"");
        }
    }







//    抢占席位
    @GetMapping("/{openid}")
    private synchronized Result get(@PathVariable String openid)
    {
//        判断用户和商品是否存在
        User user=userDao.get(openid);
        if(user==null)
        {
//            商品或用户不存在
            return new Result(0,"");
        }
        else
        {
//            判断该用户是否有未完成的订单
            Result result= slideZhanYou.getOrder(openid);
//            没有该用户的订单
            if(result.getCode()==0)
            {
//                判断是否该用户之前有无抢占的席位
                Result result1= slideZhanYou.getLocation(openid);
//                该用户之前没有抢占的席位
                if(result1.getCode()==0)
                {
//                    计算当前操作人数是否达到上限
                    Result result3=slideZhanYou.yn();
                    if(result3.getCode()==0)
                    {
                        //                    计算未过期b商品和未使用bkey的总数
                        Result result2=slideZhanYou.Sum();
//                    可以进行抢占
                        if(result2.getCode()==0)
                        {
//                        设置抢占席位
                            long time=System.currentTimeMillis();
                            stringRedisTemplate.opsForValue().set("slideweixin:"+time,openid,30,TimeUnit.SECONDS);
                            return new Result(1,"");
                        }
//                    不可进行抢占
                        else
                        {
//                        席位已经沾满
                            return new Result(2,"");
                        }
                    }
                    else
                    {
                        if(result3.getCode()==1)
                        {
                            return new Result(2,"");
                        }
                        else
                        {
                            return new Result(3,"");
                        }
                    }
                }
//                该用户之前有抢占的席位
                else
                {
//                    直接返回可以抢占
                    return new Result(1,"");
                }
            }
//            有该用户存在的未完成的订单
            else
            {
//                返回未完成的订单
                return new Result(0,result.getMessage());
            }
        }
    }
}
