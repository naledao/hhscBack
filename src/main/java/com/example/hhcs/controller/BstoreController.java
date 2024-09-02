package com.example.hhcs.controller;

import com.example.hhcs.dao.BstoreDao;
import com.example.hhcs.dao.ImageDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.ImageB;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import com.example.hhcs.util.ToBrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/bstore")
public class BstoreController {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ToBrowseHistory browseHistory;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    获取在线b级商品
    @GetMapping
    private Result getbgoods()
    {
        List<String> list=stringRedisTemplate.opsForList().range("slide",0,-1);
        if(list==null || list.size()==0)
        {
            List<Bstore> list1=new ArrayList<>();
            Bstore bstore=new Bstore();
            bstore.setOpenid("oS7eE5bkGfkuP15Utnd1T3vEhVds");
            bstore.setId(0);
            bstore.setName("滑动窗口商品");
            bstore.setPic("http://47.100.9.232:8080/startimage2.jpg");
            bstore.setDescription("如果创建订单失败，那么支付系统就已关闭，如诺需要，请联系微信---hhsc_1。");
            bstore.setPrice(13.0);
            bstore.setBusiness("白箱");
            bstore.setArea("南区");
            bstore.setStatus(0);
            bstore.setOpenid("oS7eE5bkGfkuP15Utnd1T3vEhVds");
            list1.add(bstore);
            return new Result(1, Collections.singletonList(list1));
        }
        else
        {
            List<Bstore> list1=new ArrayList<>();
            for(String i:list)
            {
                String zan=i.split(":")[0];
                Bstore bstore=bstoreDao.getbykey(zan);
                if(bstore!=null)
                {
                    list1.add(bstore);
                }
            }
            return new Result(1, Collections.singletonList(list1));
        }
    }


//    添加b级商品浏览记录
    @PostMapping("/{openid}/{storeid}")
    private void addbhis(@PathVariable String openid,@PathVariable int storeid)
    {
        User user=userDao.get(openid);
        Bstore bstore=bstoreDao.getbyid(storeid);
        if(user!=null && bstore!=null)
        {
            browseHistory.addhistory("b",user.getWarehouse(),bstore.getId());
        }
    }


//    请求b级商品图片
    @GetMapping("/image/{goodid}")
    private ImageB getimageb(@PathVariable int goodid)
    {
        Bstore bstore=bstoreDao.getbyid(goodid);
        if(bstore==null)
        {
            ImageB imageB=new ImageB();
            imageB.setId(0);
            imageB.setImg1("http://47.100.9.232:8080/startimage2.jpg");
            return imageB;
        }
        else
        {
            ImageB imageB=imageDao.getb(bstore.getKeynm());
            if(imageB==null)
            {
                imageB=new ImageB();
                imageB.setId(0);
                imageB.setImg1("http://47.100.9.232:8080/startimage2.jpg");
                return imageB;
            }
            else
            {
                return imageB;
            }
        }
    }
}
