package com.example.hhcs.controller;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import com.example.hhcs.util.MsgCare;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msg")
public class MessageController {
    @Autowired
    private MsgCare msgCare;
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private MsgDao msgDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
//    获取消息通道
    @GetMapping("/channel/{openid}")
    private Result GetMessagechannel(@PathVariable String openid)
    {
        User user=userDao.get(openid);
        if(user==null)
        {
            return new Result(0,"");
        }
        else
        {
            Set<String> set=stringRedisTemplate.keys("tablename:"+openid+":*");
            if(set==null || set.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                List<MessageChannel> list=new LinkedList<>();
                for(String i:set)
                {
                    String id=i.split(":")[2];

                    if(id.length()<13)
                    {
                        if(id.charAt(0)=='s')
                        {
                            Sstore sstore= sstoreDao.getbyid(Integer.parseInt(id.substring(1)));
                            if(sstore!=null && sstore.getName()!=null && sstore.getPic()!=null)
                            {
                                MessageChannel messageChannel=new MessageChannel();
                                messageChannel.setName(sstore.getName());
                                messageChannel.setPic(sstore.getPic());
                                messageChannel.setId(id);
                                Message msg=msgDao.getmsg(id);
                                if(msg==null || msg.getMsg()==null)
                                {
                                    messageChannel.setLastmsg("暂无最新消息");
                                }
                                else
                                {
                                    User user1=userDao.get(msg.getOpenid());
                                    if(user1!=null && user1.getName()!=null)
                                    {
                                        StringBuilder stringBuilder=new StringBuilder();
                                        stringBuilder.append("[")
                                                .append(user1.getName())
                                                .append("]: ")
                                                .append(msg.getMsg());
                                        messageChannel.setLastmsg(new String(stringBuilder));
                                    }
                                    else
                                    {
                                        messageChannel.setLastmsg("暂无最新消息");
                                    }
                                }
                                list.add(messageChannel);
                            }
                        }
                        else if(id.charAt(0)=='a')
                        {
                            Sstore sstore= astoreDao.getnyid(Integer.parseInt(id.substring(1)));
                            if(sstore!=null && sstore.getName()!=null && sstore.getPic()!=null)
                            {
                                MessageChannel messageChannel=new MessageChannel();
                                messageChannel.setName(sstore.getName());
                                messageChannel.setPic(sstore.getPic());
                                messageChannel.setId(id);
                                System.out.println(id);
                                Message msg=msgDao.getmsg(id);
                                if(msg==null || msg.getMsg()==null)
                                {
                                    messageChannel.setLastmsg("暂无最新消息");
                                }
                                else
                                {
                                    User user1=userDao.get(msg.getOpenid());
                                    if(user1!=null && user1.getName()!=null)
                                    {
                                        StringBuilder stringBuilder=new StringBuilder();
                                        stringBuilder.append("[")
                                                .append(user1.getName())
                                                .append("]: ")
                                                .append(msg.getMsg());
                                        messageChannel.setLastmsg(new String(stringBuilder));
                                    }
                                    else
                                    {
                                        messageChannel.setLastmsg("暂无最新消息");
                                    }
                                }
                                list.add(messageChannel);
                            }
                        }
                        else if(id.charAt(0)=='b')
                        {
                            Bstore bstore= bstoreDao.getbyid(Integer.parseInt(id.substring(1)));
                            if(bstore!=null && bstore.getName()!=null && bstore.getPic()!=null)
                            {
                                MessageChannel messageChannel=new MessageChannel();
                                messageChannel.setName(bstore.getName());
                                messageChannel.setPic(bstore.getPic());
                                messageChannel.setId(id);
                                Message msg=msgDao.getmsg(id);
                                if(msg==null || msg.getMsg()==null)
                                {
                                    messageChannel.setLastmsg("暂无最新消息");
                                }
                                else
                                {
                                    User user1=userDao.get(msg.getOpenid());
                                    if(user1!=null && user1.getName()!=null)
                                    {
                                        StringBuilder stringBuilder=new StringBuilder();
                                        stringBuilder.append("[")
                                                .append(user1.getName())
                                                .append("]: ")
                                                .append(msg.getMsg());
                                        messageChannel.setLastmsg(new String(stringBuilder));
                                    }
                                    else
                                    {
                                        messageChannel.setLastmsg("暂无最新消息");
                                    }
                                }
                                list.add(messageChannel);
                            }
                        }
                    }
                    else
                    {
                        System.out.println(id);
                        Cstore cstore= cstoreDao.getc(id);
                        if(cstore!=null && cstore.getName()!=null && cstore.getPic()!=null)
                        {
                            MessageChannel messageChannel=new MessageChannel();
                            messageChannel.setName(cstore.getName());
                            messageChannel.setPic(cstore.getPic());
                            messageChannel.setId(id);
                            Message msg=msgDao.getmsg(id);
                            if(msg==null || msg.getMsg()==null)
                            {

                                messageChannel.setLastmsg("暂无最新消息");
                            }
                            else
                            {
                                User user1=userDao.get(msg.getOpenid());
                                if(user1!=null && user1.getName()!=null)
                                {
                                    StringBuilder stringBuilder=new StringBuilder();
                                    stringBuilder.append("[")
                                            .append(user1.getName())
                                            .append("]: ")
                                            .append(msg.getMsg());
                                    messageChannel.setLastmsg(new String(stringBuilder));
                                }
                                else
                                {
                                    messageChannel.setLastmsg("暂无最新消息");
                                }
                            }
                            list.add(messageChannel);
                        }
                    }
                }
                return new Result(1,Collections.singletonList(list));
            }
        }
    }


//    标记在通道内
    @PostMapping("/channel/y/{channelid}/{openid}")
    private void flagY(@PathVariable String channelid,@PathVariable String openid)
    {
        stringRedisTemplate.opsForValue().set("channel:"+channelid+":"+openid,"",1,TimeUnit.HOURS);
    }

//    标记不在通道内
    @PostMapping("/channel/n/{channelid}/{openid}")
    private void flagN(@PathVariable String channelid,@PathVariable String openid)
    {
        stringRedisTemplate.delete("channel:"+channelid+":"+openid);
    }


//    插入用户发送的信息
    @PostMapping("/channel/{channelid}/{openid}")
    private int insert(@PathVariable String channelid,@PathVariable String openid,@RequestParam("msg") String msg)
    {
        User user= userDao.get(openid);
        if(msg!=null && msg.length()<=200 && msg.length()>0 && user!=null)
        {
            try {
                int sum= msgDao.sum(channelid);
                if(sum>=120)
                {
                    return 2;
                }
            }catch (Exception e)
            {
                return 0;
            }
            Set<String> set=stringRedisTemplate.keys("tablename:"+openid+":"+channelid);
            if(set!=null)
            {
                Date date = new Date(); // this object contains the current date value
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=formatter.format(date);
                if(user.getHead()==null)
                {
                    try {
                        int res= msgDao.add(channelid,null,openid,msg,time,"");
                        if(res==1)
                        {
                            Set<String> set1=stringRedisTemplate.keys("channel:"+channelid+":*");
                            if(set1!=null && set1.size()!=2)
                            {
                                String zan="";
                                for (String i:set1)
                                {
                                    zan=i.split(":")[2];
                                }
                                msgCare.send(channelid,zan,msg);
                            }
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }catch (Exception e)
                    {
                        return 0;
                    }
                }
                else
                {
                    try {
                        int res= msgDao.add(channelid,null,openid,msg,time,user.getHead());
                        if(res==1)
                        {
                            Set<String> set1=stringRedisTemplate.keys("channel:"+channelid+":*");
                            if(set1!=null && set1.size()!=2)
                            {
                                String zan="";
                                for (String i:set1)
                                {
                                    zan=i.split(":")[2];
                                }
                                msgCare.send(channelid,zan,msg);
                            }
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        return 0;
                    }
                }
            }
            else
            {
                return 0;
            }
        }
        else
        {
            System.out.println(1);
            return 0;
        }
    }


//    获取通道消息
    @GetMapping("/{channelid}")
    private Result get(@PathVariable String channelid)
    {
        List<Message> list=new LinkedList<>();
        try {
            list= msgDao.get(channelid);
            if(list==null || list.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                return new Result(1,Collections.singletonList(list));
            }
        }catch (Exception e)
        {
            return new Result(0,"");
        }
    }


//    验证通道是否存在
    @GetMapping("/yn/{channelid}/{openid}")
    private int yn(@PathVariable String channelid,@PathVariable String openid)
    {
        Set<String> set=stringRedisTemplate.keys("tablename:"+openid+":"+channelid);
        if(set==null || set.size()==0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
