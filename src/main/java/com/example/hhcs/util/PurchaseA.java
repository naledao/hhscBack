package com.example.hhcs.util;

import com.example.hhcs.dao.AstoreDao;
import com.example.hhcs.dao.PurchaseHistoryDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.PurchaseHistory;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PurchaseA {
    @Autowired
    private ZhanUtil zhanUtil;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AstoreDao astoreDao;


    //    A级商品的求购占有席位
    public Result getweizhi(String openid)
    {
        Sstore sstore= astoreDao.get();
        User user= userDao.get(openid);
        if(sstore==null || user==null)
        {
//            商品不存在
            return new Result(0,"");
        }
        else
        {
            if(sstore.getOpenid().equals(openid))
            {
                //卖家无法购买自己的商品
                return new Result(3,"");
            }
            else
            {
                if(sstore.getStatus()==0)
                {
                    //            该商品不可求购
                    return new Result(0,"");
                }
                else
                {
                    String people=stringRedisTemplate.opsForValue().get("Astore:flag");
                    if(people==null)
                    {
//                可以求购
                        double price=sstore.getPurchase_price();
                        stringRedisTemplate.opsForValue().set("Astore:flag","1",30, TimeUnit.SECONDS);
                        stringRedisTemplate.opsForValue().set("Astore:openid",openid,30, TimeUnit.SECONDS);
                        return new Result(1,String.valueOf(price+1));
                    }
                    else if(people.equals("0")){
//                可以求购
                        double price=sstore.getPurchase_price();
                        stringRedisTemplate.opsForValue().set("Astore:flag","1",30, TimeUnit.SECONDS);
                        stringRedisTemplate.opsForValue().set("Astore:openid",openid,30, TimeUnit.SECONDS);
                        return new Result(1,String.valueOf(price+1));
                    }
                    else
                    {
                        String opid=stringRedisTemplate.opsForValue().get("Astore:openid");
                        if(openid.equals(opid))
                        {
//                    可以求购
                            double price=sstore.getPurchase_price();
                            return new Result(1,String.valueOf(price+1));
                        }
                        else
                        {
//                    不可以求购
                            return new Result(2,"");
                        }
                    }
                }
            }
        }
    }

    //    A级商品确认求购
    @Transactional
    public int confirmA(String openid)
    {
        String people=stringRedisTemplate.opsForValue().get("Astore:openid");
        if(people==null)
        {
            System.out.println(0);
//            求购席位超市，重新求购
            return 0;
        }
        else if(!people.equals(openid))
        {
            System.out.println(1);
//            求购席位超市，重新求购
            return 0;
        }
        else
        {
//            可以求购，修改求购价格
            Sstore sstore= astoreDao.get();
            double price=sstore.getPurchase_price();
            int res=astoreDao.uppurch(price+1,openid);
            if(res==1)
            {
                zhanUtil.zhan("a","a"+sstore.getId(),openid,price+1);
//                求购成功,更新求购价以及求购者,添加到求购历史，删除求购参标量
                PurchaseHistory purchaseHistory=userDao.gethis(openid);
                if(purchaseHistory==null)
                {
                    PurchaseHistory purchaseHistory1=new PurchaseHistory();
                    purchaseHistory1.setOpenid(openid);
                    purchaseHistory1.setA(sstore.getId());
                    userDao.xinhis(purchaseHistory1);
                    stringRedisTemplate.delete("Astore:flag");
                    stringRedisTemplate.delete("Astore:openid");
                }
                else
                {
                    purchaseHistory.setA(sstore.getId());
                    userDao.upAhis(sstore.getId(),openid);
                    stringRedisTemplate.delete("Astore:flag");
                    stringRedisTemplate.delete("Astore:openid");
                }
                String sellerid=sstore.getOpenid();
                if(sellerid!=null && !sellerid.equals(""))
                {
                    User user= userDao.get(sellerid);
                    if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                    {
                        if(sstore.getName()!=null && !sstore.getName().equals(""))
                        {
                            User user1=userDao.get(openid);
                            if(user1!=null && user1.getName()!=null && !user1.getName().equals(""))
                            {
                                StringBuilder stringBuilder=new StringBuilder();
                                stringBuilder.append("你的商品")
                                        .append("【")
                                        .append(sstore.getName())
                                        .append("】")
                                        .append("得到了用户")
                                        .append("【")
                                        .append(user1.getName())
                                        .append("】")
                                        .append("的求购,求购价为")
                                        .append("【")
                                        .append(price+1)
                                        .append("】")
                                        .append("元,你可以上线选择结束求购或者继续等待");
                                MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】你的商品得到了求购");
                            }
                        }
                    }
                }
                return 1;
            }
            else
            {
//                求购失败，请重试
                return 2;
            }
        }
    }


//    a商品求购记录
    public Result geta(String openid)
    {
        String nowopenid=stringRedisTemplate.opsForValue().get("roll");
        if(nowopenid==null)
        {
            return new Result(0,"");
        }
        else
        {
            PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(openid);
            if(purchaseHistory==null || purchaseHistory.getA()==0)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= astoreDao.get();
                if(sstore!=null && sstore.getId()==purchaseHistory.getA() && sstore.getPurchase_people()!=null && sstore.getPurchase_people().equals(openid))
                {
                    List<Sstore> ing=new ArrayList<>();
                    List<Sstore> end=new ArrayList<>();
                    if(sstore.getStatus()==0)
                    {
                        end.add(sstore);
                    }
                    else
                    {
                        ing.add(sstore);
                    }
                    return new Result(1, Collections.singletonList(ing),Collections.singletonList(end));
                }
                else
                {
                    return new Result(0,"");
                }
            }
        }
    }
}
