package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgCare {
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private CstoreDao cstoreDao;
    public void send(String channelid,String openid,String msg)
    {
        if(channelid.length()<=13)
        {
            if(channelid.charAt(0)=='s')
            {
                Sstore sstore= sstoreDao.getbyid(Integer.parseInt(channelid.substring(1)));
                if(sstore!=null && sstore.getPurchase_people()!=null && sstore.getOpenid()!=null && sstore.getName()!=null)
                {
                    if(openid.equals(sstore.getOpenid()))
                    {
                        User user= userDao.get(sstore.getPurchase_people());
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("【")
                                    .append(sstore.getName())
                                    .append("】通道有一条新消息")
                                    .append("【")
                                    .append(msg)
                                    .append("】,请及时上线回复");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                        }
                    }
                    else
                    {
                        User user= userDao.get(sstore.getOpenid());
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("【")
                                    .append(sstore.getName())
                                    .append("】通道有一条新消息")
                                    .append("【")
                                    .append(msg)
                                    .append("】,请及时上线回复");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                        }
                    }
                }
            }
            else if(channelid.charAt(0)=='a')
            {
                Sstore sstore= astoreDao.getnyid(Integer.parseInt(channelid.substring(1)));
                if(sstore!=null && sstore.getPurchase_people()!=null && sstore.getOpenid()!=null && sstore.getName()!=null)
                {
                    if(openid.equals(sstore.getOpenid()))
                    {
                        User user= userDao.get(sstore.getPurchase_people());
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("【")
                                    .append(sstore.getName())
                                    .append("】通道有一条新消息")
                                    .append("【")
                                    .append(msg)
                                    .append("】,请及时上线回复");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                        }
                    }
                    else
                    {
                        User user= userDao.get(sstore.getOpenid());
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("【")
                                    .append(sstore.getName())
                                    .append("】通道有一条新消息")
                                    .append("【")
                                    .append(msg)
                                    .append("】,请及时上线回复");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                        }
                    }
                }
            }
            else if(channelid.charAt(0)=='b')
            {
                Bstore bstore= bstoreDao.getbyid(Integer.parseInt(channelid.substring(1)));
                if(bstore!=null && bstore.getPurchase_people()!=null && bstore.getOpenid()!=null && bstore.getName()!=null)
                {
                    if(openid.equals(bstore.getOpenid()))
                    {
                        User user= userDao.get(bstore.getPurchase_people());
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("【")
                                    .append(bstore.getName())
                                    .append("】通道有一条新消息")
                                    .append("【")
                                    .append(msg)
                                    .append("】,请及时上线回复");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                        }
                    }
                    else
                    {
                        User user= userDao.get(bstore.getOpenid());
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("【")
                                    .append(bstore.getName())
                                    .append("】通道有一条新消息")
                                    .append("【")
                                    .append(msg)
                                    .append("】,请及时上线回复");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                        }
                    }
                }
            }
        }
        else
        {
            Cstore cstore= cstoreDao.getc(channelid);
            if(cstore!=null && cstore.getPurchase_people()!=null && cstore.getOpenid()!=null && cstore.getName()!=null)
            {
                if(openid.equals(cstore.getOpenid()))
                {
                    User user= userDao.get(cstore.getPurchase_people());
                    if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                    {
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append("【")
                                .append(cstore.getName())
                                .append("】通道有一条新消息")
                                .append("【")
                                .append(msg)
                                .append("】,请及时上线回复");
                        MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                    }
                }
                else
                {
                    User user= userDao.get(cstore.getOpenid());
                    if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                    {
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append("【")
                                .append(cstore.getName())
                                .append("】通道有一条新消息")
                                .append("【")
                                .append(msg)
                                .append("】,请及时上线回复");
                        MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】 新消息通知");
                    }
                }
            }
        }
    }
}
