package com.example.hhcs.controller;

import com.example.hhcs.dao.PayOrderDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.Pay;
import com.example.hhcs.domain.PayOrder;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import com.example.hhcs.util.MD5Util;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/roll")
public class RollController {
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    确认抢占
    @PostMapping("/{openid}/{price}")
    private synchronized Result confirm(@PathVariable String openid,@PathVariable double price)
    {
        User user= userDao.get(openid);
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String x = df.format(day);
        String time1=stringRedisTemplate.opsForValue().get("QQtime");
        if(time1==null)
        {
            return new Result(0,"");
        }
        if(user==null ||  (Integer.parseInt(x)!=Integer.parseInt(time1)))
        {
            return new Result(0,"");
        }
        String flag = stringRedisTemplate.opsForValue().get("rollweixin");
        Long time = stringRedisTemplate.getExpire("rollweixin");
        if (flag == null || !flag.equals(openid))
        {
            return new Result(0, "");
        }
        else
        {
            if (time != null && time >= 3)
            {
                String payid = System.currentTimeMillis()+"a";
                int type = 1;
                String param = openid+"*a";
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
                            stringRedisTemplate.opsForValue().set("rollzhifu:image",stu.getData().getOrderId(),295,TimeUnit.SECONDS);
                            stringRedisTemplate.opsForValue().set("rollzhifu:openid",openid,295,TimeUnit.SECONDS);
                            PayOrder payOrder=new PayOrder();
                            payOrder.setPayid(String.valueOf(payid));
                            payOrder.setCloundid(stu.getData().getOrderId());
                            payOrder.setOpenid(openid);
                            payOrderDao.add(payOrder);
                            stringRedisTemplate.opsForValue().set("rollroom","",10,TimeUnit.SECONDS);
                            return new Result(1, stu.getData().getOrderId());
                        }
                        else
                        {
                            return new Result(0, "");
                        }
                    }
                    else
                    {
                        return new Result(0,"");
                    }
                } catch (IOException e) {
                    return new Result(0,"");
                }
            }
            else
            {
                return new Result(0, "");
            }
        }
    }





















//    抢占支付席位
    @GetMapping("/{openid}")
    private synchronized Result get(@PathVariable String openid){
        User user= userDao.get(openid);
//        当前key的生成时间
        String room =stringRedisTemplate.opsForValue().get("rollroom");
        if(user==null || room!=null)
        {
//            抢占失败
            return new Result(1,"");
        }
        String dingdan=stringRedisTemplate.opsForValue().get("rollzhifu:openid");
        if(dingdan==null)
        {
            String flag=stringRedisTemplate.opsForValue().get("rollweixin");
            if(flag==null || flag.equals(openid))
            {
//            可以操作
                String roll=stringRedisTemplate.opsForValue().get("roll");
                if(roll==null)
                {
//            现在没有商品在首页,可以进行抢占
//            判断a级商品的key有几个，0个代表可以抢占
                    Set<String> nowopenid=stringRedisTemplate.keys("akey:*");
                    if(nowopenid==null || nowopenid.size()==0)
                    {
//                可以抢占

                        stringRedisTemplate.opsForValue().set("rollweixin",openid,30, TimeUnit.SECONDS);
                        return new Result(0,"");
                    }
                    else
                    {
                        List<String> list=new ArrayList<>(nowopenid);
                        String key=list.get(0).split(":")[1];
                        PayOrder payOrder= payOrderDao.y(key);
                        if(payOrder==null)
                        {

                            return new Result(1,"");
                        }
                        else
                        {
                            if(payOrder.getOpenid().equals(openid))
                            {

                                //                            请尽快使用密匙
                                return new Result(10,"");
                            }
                            else
                            {

                                return new Result(1,"");
                            }
                        }
                    }
                }
                if(roll.equals(openid))
                {

//            本人已经抢占
                    return new Result(2,"");
                }
                else
                {

//            不可以抢占
                    return new Result(1,"");
                }
            }
            else
            {

//                有人正在操作
                return new Result(3,"");
            }
        }
        else
        {
            if(dingdan.equals(openid))
            {
                String image=stringRedisTemplate.opsForValue().get("rollzhifu:image");
                if(image==null)
                {

//                    操作超时
                    return new Result(4,"");
                }
                else
                {

//                    返回订单二维码
                    return new Result(5,image);
                }
            }
            else
            {

                return new Result(3,"");
            }
        }
    }
}
