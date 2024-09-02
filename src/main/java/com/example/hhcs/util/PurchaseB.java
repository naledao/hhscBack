package com.example.hhcs.util;

import com.example.hhcs.dao.BstoreDao;
import com.example.hhcs.dao.PurchaseHistoryDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.dao.ZhanDao;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.PurchaseHistory;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class PurchaseB {
    @Autowired
    private ZhanUtil zhanUtil;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BstoreDao bstoreDao;

//    抢占位置
    public synchronized Result getweizhi(String openid,int goodid)
    {
        User user= userDao.get(openid);
        Bstore bstore= bstoreDao.getbyid(goodid);
        if(user==null || bstore==null)
        {
            return new Result(0,"");
        }
        else
        {
            if(user.getOpenid().equals(bstore.getOpenid()))
            {
//                不能求购自己发布的产品
                return new Result(3,"");
            }
            else
            {
                if(bstore.getStatus()==0)
                {
                    //            该商品不可求购
                    return new Result(0,"");
                }
                else
                {
                    //            判断前面是否有人
                    String nowopenid=stringRedisTemplate.opsForValue().get("Bgood:"+goodid);
                    if(nowopenid==null)
                    {
//                没有人，占有席位
                        stringRedisTemplate.opsForValue().set("Bgood:"+goodid,openid,30, TimeUnit.SECONDS);
                        String now=stringRedisTemplate.opsForValue().get("Bgood:"+goodid);
                        if(now!=null && now.equals(openid))
                        {
//                    占有席位成功，返回信息
                            return new Result(1,String.valueOf(bstore.getPurchase_price()+1));
                        }
                        else
                        {
                            return new Result(241,"");
                        }
                    }
                    else
                    {
//                判断是否是同一个人
                        if(nowopenid.equals(openid))
                        {
                            return new Result(1,String.valueOf(bstore.getPurchase_price()+1));
                        }
                        else
                        {
                            return new Result(2,"");
                        }
                    }
                }
            }
        }
    }

//    确认求购
    @Transactional
    public int confirmA(String openid,int goodid)
    {
        String nowopenid=stringRedisTemplate.opsForValue().get("Bgood:"+goodid);
        if(nowopenid==null)
        {
//            位置抢占超时
            return 0;
        }
        else
        {
            if(!nowopenid.equals(openid))
            {
                return 0;
            }
            else
            {
//                可以修改
//                更新求购人和求购价
                Bstore bstore= bstoreDao.getbyid(goodid);
                double price=bstore.getPurchase_price()+1;
                int res=bstoreDao.uppur(price,openid,goodid);
                if(res==1)
                {
                    zhanUtil.zhan("b","b"+bstore.getId(),openid,price);
                    if(bstore.getOpenid()!=null && !bstore.getOpenid().equals(""))
                    {
                        String sellerid=bstore.getOpenid();
                        User seller=userDao.get(sellerid);
                        if(seller!=null && seller.getEmail()!=null && !seller.getEmail().equals(""))
                        {
                            if(bstore.getName()!=null && !bstore.getName().equals(""))
                            {
                                User buyer= userDao.get(openid);
                                if(buyer!=null && buyer.getName()!=null && !buyer.getName().equals(""))
                                {
                                    StringBuilder stringBuilder=new StringBuilder();
                                    stringBuilder.append("你的商品")
                                            .append("【")
                                            .append(bstore.getName())
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
                                    MailUtils.sendMail(seller.getEmail(),new String(stringBuilder),"【黄淮市场】你的商品得到了求购");
                                }
                            }
                        }
                    }
//                    求购成功，添加求购记录
                    String purchaseHistory=purchaseHistoryDao.getyn(openid);
                    if(purchaseHistory==null)
                    {
//                        初始化求购记录
                        PurchaseHistory purchaseHistory1=new PurchaseHistory();
                        purchaseHistory1.setOpenid(openid);
                        purchaseHistory1.setB(goodid+"-");
                        purchaseHistoryDao.addpeop(purchaseHistory1);
                        stringRedisTemplate.delete("Bgood:"+goodid);
                        return 1;
                    }
                    else
                    {
                        String bhis=purchaseHistoryDao.gethis(openid).getB();
                        if(bhis==null || bhis.equals(""))
                        {
                            purchaseHistoryDao.upb(goodid+"-",openid);
                            stringRedisTemplate.delete("Bgood:"+goodid);
                            return 1;
                        }
                        else
                        {
                            String[] x=bhis.split("-");
                            for(int m=0;m<x.length;m++)
                            {
                                if(x[m].equals(String.valueOf(goodid)))
                                {
                                    stringRedisTemplate.delete("Bgood:"+goodid);
                                    return 1;
                                }
                            }
                            if(x.length>=3)
                            {
                                for(int i=0;i<x.length;i++)
                                {
                                    Bstore bstore1=bstoreDao.getbyid(goodid);
                                    if(bstore1==null)
                                    {
                                        x[i]=String.valueOf(goodid);
                                        break;
                                    }
                                    else
                                    {
                                        String key="timeslide:"+bstore1.getKeynm();
                                        String v=stringRedisTemplate.opsForValue().get(key);
                                        if(v==null)
                                        {
                                            x[i]=String.valueOf(goodid);
                                            break;
                                        }
                                    }
                                }
                                StringBuilder stringBuilder=new StringBuilder();
                                for(String l:x)
                                {
                                    stringBuilder.append(l).append("-");
                                }
                                purchaseHistoryDao.upb(new String(stringBuilder),openid);
                                stringRedisTemplate.delete("Bgood:"+goodid);
                                return 1;
                            }
                            else
                            {
                                StringBuilder stringBuilder=new StringBuilder();
                                for(String k:x)
                                {
                                    stringBuilder.append(k).append("-");
                                }
                                stringBuilder.append(goodid).append("-");
                                purchaseHistoryDao.upb(new String(stringBuilder),openid);
                                stringRedisTemplate.delete("Bgood:"+goodid);
                                return 1;
                            }
                        }
                    }
                }
                else
                {
                    return 2;
                }
            }
        }
    }



//    求购记录
    public Result getb(String openid)
    {
        List<String> list=stringRedisTemplate.opsForList().range("slide",0,-1);
        if(list==null || list.size()==0)
        {
            return new Result(0,"");
        }
        else
        {
            PurchaseHistory purchaseHistory= purchaseHistoryDao.gethis(openid);
            if(purchaseHistory==null || purchaseHistory.getB()==null || purchaseHistory.getB().equals(""))
            {
                return new Result(0,"");
            }
            else
            {
                List<Bstore> ing=new ArrayList<>();
                List<Bstore> end=new ArrayList<>();
                String[] hisb=purchaseHistory.getB().split("-");
                Set<String> set=new HashSet<>();
                for(String key:list)
                {
                    set.add(key.split(":")[0]);
                }
                for(String i:hisb)
                {
                    Bstore bstore=bstoreDao.getbyid(Integer.parseInt(i));
                    if(bstore!=null && bstore.getKeynm()!=null && set.contains(bstore.getKeynm()))
                    {
                        if(bstore.getPurchase_people()!=null && bstore.getPurchase_people().equals(openid) && bstore.getStatus()==0)
                        {
                            end.add(bstore);
                        }
                        else
                        {
                            if(bstore.getPurchase_people()!=null && bstore.getPurchase_people().equals(openid))
                            {
                                ing.add(bstore);
                            }
                        }
                    }
                }
                return new Result(1, Collections.singletonList(ing),Collections.singletonList(end));
            }
        }
    }
}
