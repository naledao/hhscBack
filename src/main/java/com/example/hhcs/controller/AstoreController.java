package com.example.hhcs.controller;

import com.example.hhcs.dao.AstoreDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.*;
import com.example.hhcs.service.ImageService;
import com.example.hhcs.util.ToBrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astore")
public class AstoreController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ToBrowseHistory toBrowseHistory;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    获取滚动商品的描述信息
    @GetMapping()
    private Result getdes()
    {
        String openid=stringRedisTemplate.opsForValue().get("roll");
        if(openid==null)
        {
            Sstore sstore=new Sstore();
            sstore.setName("滚动播放商品");
            sstore.setDescription("如果创建订单失败，那么支付系统就已关闭，如诺需要，请联系微信---hhsc_1。");
            sstore.setPrice(26.3);
            sstore.setBusiness("白箱");
            sstore.setArea("南区");
            sstore.setStatus(0);
            sstore.setOpenid("oS7eE5bkGfkuP15Utnd1T3vEhVds");
            return new Result(1,sstore);
        }
        else
        {
            Sstore sstore1=astoreDao.get();
            if(sstore1==null)
            {
                Sstore sstore=new Sstore();
                sstore.setName("滚动播放商品");
                sstore.setDescription("如果创建订单失败，那么支付系统就已关闭，如诺需要，请联系微信---hhsc_1。");
                sstore.setPrice(26.3);
                sstore.setBusiness("白箱");
                sstore.setArea("南区");
                sstore.setStatus(0);
                sstore.setOpenid("oS7eE5bkGfkuP15Utnd1T3vEhVds");
                return new Result(1,sstore);
            }
            else
            {
                return new Result(1,sstore1);
            }
        }
    }

//    添加a商品的浏览记录
    @PostMapping("/{openid}")
    private void addbrowseahistory(@PathVariable String openid)
    {
//        获取用户id
        User user= userDao.get(openid);
//        获取a级商品id
        Sstore sstore= astoreDao.get();
        if(sstore!=null && user!=null)
        {
            toBrowseHistory.addhistory("a",user.getWarehouse(),sstore.getId());
        }
    }

//    获取a级商品图片
    @GetMapping("/image")
    private ImageS getaimage()
    {
        return imageService.getaimage();
    }
}
