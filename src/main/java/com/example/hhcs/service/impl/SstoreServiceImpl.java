package com.example.hhcs.service.impl;

import com.example.hhcs.dao.SstoreDao;
import com.example.hhcs.dao.ZhanDao;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.Zhan;
import com.example.hhcs.service.PurchaseHistoryServive;
import com.example.hhcs.service.SstoreService;
import com.example.hhcs.util.ZhanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SstoreServiceImpl implements SstoreService {
    @Autowired
    private ZhanUtil zhanUtil;
    @Autowired
    private ZhanDao zhanDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PurchaseHistoryServive purchaseHistoryServive;
    @Override
    public Sstore get() {
        String start=stringRedisTemplate.opsForValue().get("start");
        if(start==null)
        {
            Sstore sstore=new Sstore();
            sstore.setName("启动页商品");
            sstore.setPic("http://47.100.9.232:8080/hhsc/start/pic/default2.jpg");
            sstore.setDescription("如果创建订单失败，那么支付系统就已关闭，如诺需要，请联系微信---hhsc_1。");
            sstore.setImage(1);
            sstore.setPrice(4.6);
            sstore.setPurchase_price(0);
            sstore.setBusiness("白箱");
            sstore.setArea("南区");
            sstore.setStatus(0);
            sstore.setOpenid("oS7eE5bkGfkuP15Utnd1T3vEhVds");
            return sstore;
        }
        else
        {
            return sstoreDao.get();
        }
    }

    @Override
    public int getstatus() {
        return sstoreDao.getstatus();
    }

    @Override
    public double getpurchase() {
        return sstoreDao.getpurchase();
    }

    @Override
    public String getopenid() {
        return sstoreDao.getopenid();
    }

//    用户确认求购后更新商品求购者以及求购者的求购历史
    @Transactional
    @Override
    public int updatepur(double Purchase_price,String Purchase_people) {
        int a=sstoreDao.updatepur(Purchase_price);
        int b=sstoreDao.updateppeop(Purchase_people);
        Sstore sstore= sstoreDao.get();
        int c=purchaseHistoryServive.ups(sstore.getId(),Purchase_people);
        Boolean d=stringRedisTemplate.delete("Sstore:flag");
        Boolean e=stringRedisTemplate.delete("Sstore:openid");
        zhanUtil.zhan("s","s"+sstore.getId(),Purchase_people,Purchase_price);
        if(d && e)
        {
            return a+b+c;
        }
        else
        {
            return 0;
        }
    }
}
