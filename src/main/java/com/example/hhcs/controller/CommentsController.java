package com.example.hhcs.controller;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import com.example.hhcs.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private MsgDao msgDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private SstoreDao sstoreDao;
//    发送评论
    @PostMapping("/send/{rate}")
    private int SendComments(@RequestParam("goodid") String id, @RequestParam("people") String people, @RequestParam("str") String str, @PathVariable String rate,@RequestParam("buyer") String buyer)
    {
        if(people!=null && buyer!=null && str!=null && !str.equals("") && rate!=null && str.length()<=1000)
        {
            User user= userDao.get(people);
            User buy=userDao.get(buyer);
            if(user!=null && user.getHead()!=null && user.getName()!=null && buy!=null && id!=null)
            {
                if(rate.equals("c"))
                {
                    Cstore cstore= cstoreDao.getc(id);
                    if(cstore!=null && cstore.getName()!=null)
                    {
                        id=id.replace("-","");
                        tableMapper.createComments("com_"+id);
                        Date date = new Date(); // this object contains the current date value
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time=formatter.format(date);
                        int g=msgDao.addCom("com_"+id,null,people, user.getName(),str,time,user.getHead(),user.getWarehouse());
                        if(g==1)
                        {
                            if(buy.getEmail()!=null && !buy.getEmail().equals(""))
                            {
                                String stringBuilder = "【" +
                                        cstore.getName() +
                                        "】" +
                                        "下有一条新的留言";
                                MailUtils.sendMail(buy.getEmail(), stringBuilder,"【黄淮市场】新留言通知");
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
                else if(rate.equals("b"))
                {
                    Bstore bstore= bstoreDao.getbykey(id);
                    if(bstore!=null && bstore.getKeynm()!=null && bstore.getName()!=null)
                    {
                        String s = stringRedisTemplate.opsForValue().get("timeslide:" + bstore.getKeynm());
                        if(s!=null)
                        {
                            tableMapper.createComments("com_"+bstore.getKeynm());
                            Date date = new Date(); // this object contains the current date value
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int g=msgDao.addCom("com_"+bstore.getKeynm(),null,people, user.getName(), str,time,user.getHead(),user.getWarehouse());
                            if(g==1)
                            {
                                if(buy.getEmail()!=null && !buy.getEmail().equals(""))
                                {
                                    String stringBuilder = "【" +
                                            bstore.getName() +
                                            "】" +
                                            "下有一条新的留言";
                                    MailUtils.sendMail(buy.getEmail(), stringBuilder,"【黄淮市场】新留言通知");
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
                    else
                    {
                        return 0;
                    }
                }
                else if(rate.equals("a"))
                {
                    Sstore sstore =astoreDao.get();
                    if(sstore!=null && sstore.getOpenid().equals(buyer) && sstore.getName()!=null)
                    {
                        String s = stringRedisTemplate.opsForValue().get("roll");
                        if(s!=null && s.equals(buyer))
                        {
                            tableMapper.createComments("com_a"+sstore.getId());
                            Date date = new Date(); // this object contains the current date value
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int g=msgDao.addCom("com_a"+sstore.getId(),null,people, user.getName(), str,time,user.getHead(),user.getWarehouse());
                            if(g==1)
                            {
                                if(buy.getEmail()!=null && !buy.getEmail().equals(""))
                                {
                                    String stringBuilder = "【" +
                                            sstore.getName() +
                                            "】" +
                                            "下有一条新的留言";
                                    MailUtils.sendMail(buy.getEmail(), stringBuilder,"【黄淮市场】新留言通知");
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
                    else
                    {
                        return 0;
                    }
                }
                else if(rate.equals("s"))
                {
                    Sstore sstore =sstoreDao.get();
                    if(sstore!=null && sstore.getOpenid().equals(buyer) && sstore.getName()!=null)
                    {
                        String s = stringRedisTemplate.opsForValue().get("start");
                        if(s!=null && s.equals(buyer))
                        {
                            tableMapper.createComments("com_s"+sstore.getId());
                            Date date = new Date(); // this object contains the current date value
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int g=msgDao.addCom("com_s"+sstore.getId(),null,people, user.getName(), str,time,user.getHead(),user.getWarehouse());
                            if(g==1)
                            {
                                if(buy.getEmail()!=null && !buy.getEmail().equals(""))
                                {
                                    String stringBuilder = "【" +
                                            sstore.getName() +
                                            "】" +
                                            "下有一条新的留言";
                                    MailUtils.sendMail(buy.getEmail(), stringBuilder,"【黄淮市场】新留言通知");
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


//    获取评论
    @GetMapping("/get/{rate}")
    private List<Comments> getmsg(@RequestParam("goodid") String id, @PathVariable String rate)
    {
        if(rate!=null)
        {
            if(rate.equals("c"))
            {
                Cstore cstore= cstoreDao.getc(id);
                if(cstore!=null)
                {
                    try {
                        id=id.replace("-","");
                        List<Comments> list=msgDao.getCom("com_"+id);
                        if(list!=null)
                        {
                            return list;
                        }
                        else
                        {
                            return new ArrayList<>();
                        }
                    }catch (Exception e)
                    {
                        return new ArrayList<>();
                    }
                }
                else
                {
                    return new ArrayList<>();
                }
            }
            else if(rate.equals("b"))
            {
                try {
                    Bstore bstore=bstoreDao.getbykey(id);
                    if(bstore!=null)
                    {
                        List<Comments> list=msgDao.getCom("com_"+id);
                        if(list!=null)
                        {
                            return list;
                        }
                        else
                        {
                            return new ArrayList<>();
                        }
                    }
                    else
                    {
                        return new ArrayList<>();
                    }
                }catch (Exception e)
                {
                    return new ArrayList<>();
                }
            }
            else if(rate.equals("a"))
            {
                Sstore sstore=astoreDao.get();
                if(sstore!=null && sstore.getOpenid().equals(id))
                {
                    try {
                        List<Comments> list=msgDao.getCom("com_a"+sstore.getId());
                        if(list!=null)
                        {
                            return list;
                        }
                        else
                        {
                            return new ArrayList<>();
                        }
                    }catch (Exception e)
                    {
                        return new ArrayList<>();
                    }
                }
                else
                {
                    return new ArrayList<>();
                }
            }
            else if(rate.equals("s"))
            {
                Sstore sstore= sstoreDao.get();
                if(sstore!=null && sstore.getOpenid().equals(id))
                {
                    try {
                        List<Comments> list=msgDao.getCom("com_s"+sstore.getId());
                        if(list!=null)
                        {
                            return list;
                        }
                        else
                        {
                            return new ArrayList<>();
                        }
                    }catch (Exception e)
                    {
                        return new ArrayList<>();
                    }
                }
                else
                {
                    return new ArrayList<>();
                }
            }
            else
            {
                return new ArrayList<>();
            }
        }
        else
        {
            return new ArrayList<>();
        }
    }
}
