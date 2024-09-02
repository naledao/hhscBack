package com.example.hhcs.controller;

import com.example.hhcs.dao.PayOrderDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.Key;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/checkkey")
public class KeyController {
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    验证key
    @GetMapping("")
    private Result checkkey(@RequestParam("key") String key, @RequestParam("choice") String choice)
    {
        if(choice.equals("s"))
        {
            String res=stringRedisTemplate.opsForValue().get("key:"+key);
            if(res==null)
            {
                return new Result(0,"");
            }
            else
            {
                return new Result(1,key);
            }
        }

        if(choice.equals("a"))
        {
            String res=stringRedisTemplate.opsForValue().get("akey:"+key);
            if(res==null)
            {
                return new Result(0,"");
            }
            else
            {
                return new Result(1,key);
            }
        }

        if(choice.equals("b"))
        {
            String res=stringRedisTemplate.opsForValue().get("bkey:"+key);
            if(res==null)
            {
                return new Result(0,"");
            }
            else
            {
                return new Result(1,key);
            }
        }
        else
        {
            return new Result(0,"");
        }
    }




//    获取用户key记录
    @GetMapping("/getallkey/{openid}")
    private Result getkey(@PathVariable String openid)
    {
        User user= userDao.get(openid);
        if(user==null)
        {
            return new Result(0,"");
        }
        else
        {
            List<String> list= payOrderDao.getkey(openid);
            if(list==null || list.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                List<Key> list1=new ArrayList<>();
                for(String key:list)
                {
                    if(key!=null && !key.equals(""))
                    {
                        String time=stringRedisTemplate.opsForValue().get("key:"+key);
                        String time1=stringRedisTemplate.opsForValue().get("akey:"+key);
                        String time2=stringRedisTemplate.opsForValue().get("bkey:"+key);
                        if(time==null && time1==null && time2==null)
                        {
//                        key已使用
                            Key key1=new Key();
                            key1.setKey(key);
                            key1.setTime("已使用");
                            key1.setFlag(1);
                            list1.add(key1);
                        }
                        else
                        {
                            if(time!=null)
                            {
                                Long remainingtime=stringRedisTemplate.getExpire("key:"+key);
                                Long haomiao=remainingtime*1000;
                                Key key1=new Key();
                                key1.setKey(key);
                                key1.setTime(String.valueOf(haomiao));
                                key1.setFlag(0);
                                list1.add(key1);
                            }
                            else
                            {
                                if(time1!=null)
                                {
                                    Long remainingtime=stringRedisTemplate.getExpire("akey:"+key);
                                    Long haomiao=remainingtime*1000;
                                    Key key1=new Key();
                                    key1.setKey(key);
                                    key1.setTime(String.valueOf(haomiao));
                                    key1.setFlag(0);
                                    list1.add(key1);
                                }
                                else
                                {
                                    if(time2!=null)
                                    {
                                        Long remainingtime=stringRedisTemplate.getExpire("bkey:"+key);
                                        Long haomiao=remainingtime*1000;
                                        Key key1=new Key();
                                        key1.setKey(key);
                                        key1.setTime(String.valueOf(haomiao));
                                        key1.setFlag(0);
                                        list1.add(key1);
                                    }
                                    else
                                    {
                                        Key key1=new Key();
                                        key1.setKey(key);
                                        key1.setTime("已使用");
                                        key1.setFlag(1);
                                        list1.add(key1);
                                    }
                                }
                            }
                        }
                    }
                }
                return new Result(1,Collections.singletonList(list1));
            }
        }
    }
}
