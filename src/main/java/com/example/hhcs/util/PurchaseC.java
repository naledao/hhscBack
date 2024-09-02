package com.example.hhcs.util;

import com.example.hhcs.dao.CstoreDao;
import com.example.hhcs.dao.PurchaseHistoryDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.PurchaseHistory;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PurchaseC {
    @Autowired
    private ZhanUtil zhanUtil;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CstoreDao cstoreDao;
    public synchronized Result getweizhi(String openid,String goodid)
    {
        User user= userDao.get(openid);
        Cstore cstore= cstoreDao.getc(goodid);
        if(user==null || cstore==null)
        {
//            求购失败
            return new Result(30,"");
        }
        else
        {
            if(cstore.getStatus()==1)
            {
                //            判断是否为自己发布的产品
                if(cstore.getOpenid().equals(user.getOpenid()))
                {
//                无法求购自己发布的商品
                    return new Result(31,"");
                }
                else
                {
//                判断前面是否有人操作
                    String nowopenid=stringRedisTemplate.opsForValue().get("common:"+goodid);
                    if(nowopenid==null)
                    {
//                    可以抢购
                        stringRedisTemplate.opsForValue().set("common:"+goodid,openid,30, TimeUnit.SECONDS);
                        return new Result(32,String.valueOf(cstore.getPurchase_price()+1));
                    }
                    else
                    {
                        if(nowopenid.equals(openid))
                        {
                            return new Result(32,String.valueOf(cstore.getPurchase_price()+1));
                        }
                        else
                        {
                            return new Result(33,"");
                        }
                    }
                }
            }
            else
            {
                return new Result(34,"");
            }
        }
    }

    @Transactional
    public int confirmA(String openid,String goodid)
    {
        User user= userDao.get(openid);
        Cstore cstore= cstoreDao.getc(goodid);
        if(user==null || cstore==null)
        {
//            求购失败
            return 30;
        }
        else
        {
//            判断有没有超时
            String nowopenid=stringRedisTemplate.opsForValue().get("common:"+goodid);
            if(nowopenid==null || !nowopenid.equals(openid))
            {
//                操作超时
                return 31;
            }
            else
            {
//                更新求购价和求购人
                double price=cstore.getPurchase_price()+1;
                int res=cstoreDao.uppur(price,user.getOpenid(),goodid);
                if(res==1)
                {
//                    添加求购记录
//                    判断是否要初始化求购记录
                    PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(openid);
                    if(purchaseHistory==null)
                    {
//                        初始化求购历史
                        purchaseHistory=new PurchaseHistory();
                        purchaseHistory.setC(goodid+"*");
                        purchaseHistory.setOpenid(openid);
                        purchaseHistoryDao.addpeop(purchaseHistory);
                    }
                    else
                    {
//                        判断字段是否为空
                        String x=purchaseHistory.getC();
                        if(x==null || x.equals(""))
                        {
                            x=goodid+"*";
                            purchaseHistoryDao.upc(x,openid);
                        }
                        else
                        {
                            String[] v=x.split("[*]");
                            int flag=0;
                            for(String i :v)
                            {
                                if(i.equals(goodid))
                                {
                                    flag=1;
                                    break;
                                }
                            }
                            if(flag==0)
                            {
                                x=x+goodid+"*";
                                purchaseHistoryDao.upc(x,openid);
                            }
                        }
                    }
                    stringRedisTemplate.delete("common:"+goodid);
                    zhanUtil.zhan("c",goodid,openid,price);
                    if(cstore.getOpenid()!=null && !cstore.getOpenid().equals(""))
                    {
                        String sellerid= cstore.getOpenid();
                        User Seller=userDao.get(sellerid);
                        if(Seller!=null && Seller.getEmail()!=null && !Seller.getEmail().equals(""))
                        {
                            if(cstore.getName()!=null && !cstore.getName().equals(""))
                            {
                                User buyer=userDao.get(openid);
                                if(buyer!=null && buyer.getName()!=null && !buyer.getName().equals(""))
                                {
                                    StringBuilder stringBuilder=new StringBuilder();
                                    stringBuilder.append("你的商品")
                                            .append("【")
                                            .append(cstore.getName())
                                            .append("】")
                                            .append("得到了用户")
                                            .append("【")
                                            .append(buyer.getName())
                                            .append("】")
                                            .append("的求购,求购价为")
                                            .append("【")
                                            .append(price)
                                            .append("】")
                                            .append("元,你可以上线选择结束求购或者继续等待");
                                    MailUtils.sendMail(Seller.getEmail(),new String(stringBuilder),"【黄淮市场】你的商品得到了求购");
                                }
                            }
                        }
                    }
                    return 32;
                }
                else
                {
                    return 30;
                }
            }
        }
    }


    @Transactional
    public Result getc(String openid)
    {
        PurchaseHistory purchaseHistory= purchaseHistoryDao.gethis(openid);
        if(purchaseHistory==null || purchaseHistory.getC()==null || purchaseHistory.getC().equals(""))
        {
            return new Result(0,"");
        }
        else
        {
            List<Cstore> ing=new LinkedList<>();
            List<Cstore> end=new LinkedList<>();
            String[] cid=purchaseHistory.getC().split("[*]");
            StringBuilder stringBuilder=new StringBuilder();
            for(String i:cid)
            {
                Cstore cstore= cstoreDao.getc(i);
                if(cstore!=null && cstore.getPurchase_people()!=null && cstore.getPurchase_people().equals(openid) && cstore.getStatus()==0)
                {
                    end.add(cstore);
                    stringBuilder.append(i).append("*");
                }
                else
                {
                    if(cstore!=null && cstore.getPurchase_people()!=null && cstore.getPurchase_people().equals(openid))
                    {
                        ing.add(cstore);
                        stringBuilder.append(i).append("*");
                    }
                }
            }
//            更新求购记录
            purchaseHistoryDao.upc(new String(stringBuilder),openid);
            return new Result(1, Collections.singletonList(ing),Collections.singletonList(end));
        }
    }
}
