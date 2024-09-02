package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
public class EndStore {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private SstoreDao sstoreDao;
    @Transactional
    public int end(String rate,String goodid,String openid,String purid)
    {
        if(rate.equals("s"))
        {
            Sstore sstore= sstoreDao.getbyid(Integer.parseInt(goodid));
            if(sstore==null || !sstore.getOpenid().equals(openid) || sstore.getStatus()==-1)
            {
                return 0;
            }
            else
            {
                if(sstore.getPurchase_people().equals(purid))
                {
                    int res=sstoreDao.upstatus(0,Integer.parseInt(goodid));
                    if(res==1)
                    {
//                        开启消息通道
                        String tablename="s"+goodid;
                        tableMapper.createTable(tablename);
                        stringRedisTemplate.opsForValue().set("tablename:"+openid+":"+tablename,"",3, TimeUnit.DAYS);
                        stringRedisTemplate.opsForValue().set("tablename:"+purid+":"+tablename,"",3, TimeUnit.DAYS);
                        User user= userDao.get(purid);
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals("") && sstore.getName()!=null && !sstore.getName().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("你求购的商品【")
                                    .append(sstore.getName())
                                    .append("】消息通道已开启，请尽快上线与卖家完成交易，三天后交易将自动关闭");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】交易通道开启");
                        }
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
        }
        else if(rate.equals("a"))
        {
            Sstore sstore= astoreDao.getnyid(Integer.parseInt(goodid));
            if(sstore==null || !sstore.getOpenid().equals(openid) || sstore.getStatus()==-1)
            {
                return 0;
            }
            else
            {
                if(sstore.getPurchase_people().equals(purid))
                {
                    int res=astoreDao.upstatus(0, Integer.parseInt(goodid));
                    if(res==1)
                    {
//                        开启消息通道
                        String tablename="a"+goodid;
                        tableMapper.createTable(tablename);
                        stringRedisTemplate.opsForValue().set("tablename:"+openid+":"+tablename,"",3, TimeUnit.DAYS);
                        stringRedisTemplate.opsForValue().set("tablename:"+purid+":"+tablename,"",3, TimeUnit.DAYS);
                        User user= userDao.get(purid);
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals("") && sstore.getName()!=null && !sstore.getName().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("你求购的商品【")
                                    .append(sstore.getName())
                                    .append("】消息通道已开启，请尽快上线与卖家完成交易，三天后交易将自动关闭");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】交易通道开启");
                        }
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
        }
        else if(rate.equals("b"))
        {
            Bstore bstore = bstoreDao.getbyid(Integer.parseInt(goodid));
            if(bstore==null || !bstore.getOpenid().equals(openid) || bstore.getStatus()==-1)
            {
                return 0;
            }
            else
            {
                if(bstore.getPurchase_people().equals(purid))
                {
                    int res=bstoreDao.upstatus(0,Integer.parseInt(goodid));
                    if(res==1)
                    {
//                        开启消息通道
                        String tablename="b"+goodid;
                        tableMapper.createTable(tablename);
                        stringRedisTemplate.opsForValue().set("tablename:"+openid+":"+tablename,"",3, TimeUnit.DAYS);
                        stringRedisTemplate.opsForValue().set("tablename:"+purid+":"+tablename,"",3, TimeUnit.DAYS);
                        User user= userDao.get(purid);
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals("") && bstore.getName()!=null && !bstore.getName().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("你求购的商品【")
                                    .append(bstore.getName())
                                    .append("】消息通道已开启，请尽快上线与卖家完成交易，三天后交易将自动关闭");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】交易通道开启");
                        }
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
        }
        else if(rate.equals("c"))
        {
            Cstore cstore = cstoreDao.getc(goodid);
            if(cstore==null || !cstore.getOpenid().equals(openid) || cstore.getStatus()==-1)
            {
                return 0;
            }
            else
            {
                if(cstore.getPurchase_people().equals(purid))
                {
                    int res=cstoreDao.upstatus(0,goodid);
                    if(res==1)
                    {
//                        开启消息通道
                        String tablename=goodid;
                        tableMapper.createTable(tablename);
                        stringRedisTemplate.opsForValue().set("tablename:"+openid+":"+tablename,"",3, TimeUnit.DAYS);
                        stringRedisTemplate.opsForValue().set("tablename:"+purid+":"+tablename,"",3, TimeUnit.DAYS);
                        User user= userDao.get(purid);
                        if(user!=null && user.getEmail()!=null && !user.getEmail().equals("") && cstore.getName()!=null && !cstore.getName().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("你求购的商品【")
                                    .append(cstore.getName())
                                    .append("】消息通道已开启，请尽快上线与卖家完成交易，三天后交易将自动关闭");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】交易通道开启");
                        }
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }
    }
}
