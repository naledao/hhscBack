package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.SplittableRandom;

@Component
public class ImportMail {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SstoreDao sstoreDao;
    public void giveup(String rate,String goodid,String goodname,String buyername,String nextname,double price,String nextopenid)
    {
        if(rate.equals("s"))
        {
            Sstore sstore= sstoreDao.get();
            if(sstore.getId()==Integer.parseInt(goodid) && sstore.getOpenid()!=null)
            {
                User user= userDao.get(sstore.getOpenid());
                if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,按照求购价格记录由高到底，下一个继承求购的用户为")
                            .append("【")
                            .append(nextname)
                            .append("】,")
                            .append("当前求购价为")
                            .append("【")
                            .append(price)
                            .append("】元，")
                            .append("你可以上线终止求购开启交易通道或者继续等待。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者发生变化");
                    User user1=userDao.get(nextopenid);
                    if(user1!=null && user1.getEmail()!=null && !user1.getEmail().equals(""))
                    {
                        StringBuilder stringBuilder1=new StringBuilder();
                        stringBuilder1.append("商品【")
                                .append(goodname)
                                .append("】")
                                .append("已被用户放弃求购，按照求购价格记录由高到底，下一个继承求购的用户为你，你可上线取消求购或者等待卖家开启交易通道。");
                        MailUtils.sendMail(user1.getEmail(),new String(stringBuilder1),"【黄淮市场】 你求购过的商品求购者发生变化");
                    }
                }
            }
        }
        else if(rate.equals("a"))
        {
            Sstore sstore= astoreDao.get();
            if(sstore.getId()==Integer.parseInt(goodid) && sstore.getOpenid()!=null)
            {
                User user= userDao.get(sstore.getOpenid());
                if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,按照求购价格记录由高到底，下一个继承求购的用户为")
                            .append("【")
                            .append(nextname)
                            .append("】,")
                            .append("当前求购价为")
                            .append("【")
                            .append(price)
                            .append("】元，")
                            .append("你可以上线终止求购开启交易通道或者继续等待。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者发生变化");
                    User user1=userDao.get(nextopenid);
                    if(user1!=null && user1.getEmail()!=null && !user1.getEmail().equals(""))
                    {
                        StringBuilder stringBuilder1=new StringBuilder();
                        stringBuilder1.append("商品【")
                                .append(goodname)
                                .append("】")
                                .append("已被用户放弃求购，按照求购价格记录由高到底，下一个继承求购的用户为你，你可上线取消求购或者等待卖家开启交易通道。");
                        MailUtils.sendMail(user1.getEmail(),new String(stringBuilder1),"【黄淮市场】 你求购过的商品求购者发生变化");
                    }
                }
            }
        }
        else if(rate.equals("b"))
        {
            Bstore bstore= bstoreDao.getbyid(Integer.parseInt(goodid));
            if(bstore!=null && bstore.getOpenid()!=null)
            {
                User user= userDao.get(bstore.getOpenid());
                if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,按照求购价格记录由高到底，下一个继承求购的用户为")
                            .append("【")
                            .append(nextname)
                            .append("】,")
                            .append("当前求购价为")
                            .append("【")
                            .append(price)
                            .append("】元，")
                            .append("你可以上线终止求购开启交易通道或者继续等待。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者发生变化");
                    User user1=userDao.get(nextopenid);
                    if(user1!=null && user1.getEmail()!=null && !user1.getEmail().equals(""))
                    {
                        StringBuilder stringBuilder1=new StringBuilder();
                        stringBuilder1.append("商品【")
                                .append(goodname)
                                .append("】")
                                .append("已被用户放弃求购，按照求购价格记录由高到底，下一个继承求购的用户为你，你可上线取消求购或者等待卖家开启交易通道。");
                        MailUtils.sendMail(user1.getEmail(),new String(stringBuilder1),"【黄淮市场】 你求购过的商品求购者发生变化");
                    }
                }
            }
        }
        else if(rate.equals("c"))
        {
            Cstore cstore= cstoreDao.getc(goodid);
            if(cstore!=null && cstore.getOpenid()!=null)
            {
                User user= userDao.get(cstore.getOpenid());
                if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,按照求购价格记录由高到底，下一个继承求购的用户为")
                            .append("【")
                            .append(nextname)
                            .append("】,")
                            .append("当前求购价为")
                            .append("【")
                            .append(price)
                            .append("】元，")
                            .append("你可以上线终止求购开启交易通道或者继续等待。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者发生变化");
                    User user1=userDao.get(nextopenid);
                    if(user1!=null && user1.getEmail()!=null && !user1.getEmail().equals(""))
                    {
                        StringBuilder stringBuilder1=new StringBuilder();
                        stringBuilder1.append("商品【")
                                .append(goodname)
                                .append("】")
                                .append("已被用户放弃求购，按照求购价格记录由高到底，下一个继承求购的用户为你，你可上线取消求购或者等待卖家开启交易通道。");
                        MailUtils.sendMail(user1.getEmail(),new String(stringBuilder1),"【黄淮市场】 你求购过的商品求购者发生变化");
                    }
                }
            }
        }
    }

    public void reallygiveup(String rate,String goodid,String goodname,String buyername)
    {
        if(rate.equals("s"))
        {
            Sstore sstore= sstoreDao.get();
            if(sstore.getId()==Integer.parseInt(goodid) && sstore.getOpenid()!=null)
            {
                User user= userDao.get(sstore.getOpenid());
                if(user!=null && user.getEmail()!=null)
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,现在暂时没有求购该商品的用户。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者取消求购");
                }
            }
        }
        else if(rate.equals("a"))
        {
            Sstore sstore= astoreDao.get();
            if(sstore.getId()==Integer.parseInt(goodid) && sstore.getOpenid()!=null)
            {
                User user= userDao.get(sstore.getOpenid());
                if(user!=null && user.getEmail()!=null)
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,现在暂时没有求购该商品的用户。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者取消求购");
                }
            }
        }
        else if(rate.equals("b"))
        {
            Bstore bstore= bstoreDao.getbyid(Integer.parseInt(goodid));
            if(bstore!=null && bstore.getOpenid()!=null)
            {
                User user= userDao.get(bstore.getOpenid());
                if(user!=null && user.getEmail()!=null)
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,现在暂时没有求购该商品的用户。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者取消求购");
                }
            }
        }
        else if(rate.equals("c"))
        {
            Cstore cstore= cstoreDao.getc(goodid);
            if(cstore!=null && cstore.getOpenid()!=null)
            {
                User user= userDao.get(cstore.getOpenid());
                if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("你的商品")
                            .append("【")
                            .append(goodname)
                            .append("】")
                            .append("已被用户")
                            .append("【")
                            .append(buyername)
                            .append("】")
                            .append("放弃求购,现在暂时没有求购该商品的用户。");
                    MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 商品求购者取消求购");
                }
            }
        }
    }

}
